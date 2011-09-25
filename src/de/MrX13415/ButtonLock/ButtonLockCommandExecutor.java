package de.MrX13415.ButtonLock;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.MrX13415.ButtonLock.LockedBlockGroup.LOCKED_STATE;
import de.MrX13415.ButtonLock.LockedBlockGroup.PROTECTION_MODE;


public class ButtonLockCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		boolean permissionButtonlock = false;
		boolean permissionButtonlockOp = false;
		
		if (sender instanceof Player) {
			
			if (ButtonLock.permissionHandler != null
					&& ButtonLock.configFile.usePermissions) {
				// use Permission
				if (ButtonLock.permissionHandler.permission((Player) sender, ButtonLock.PERMISSION_NODE_ButtonLock_buttonlock)){
					permissionButtonlock = true;
				}else{
					return false;
				}
				
				if (ButtonLock.permissionHandler.permission((Player) sender, ButtonLock.PERMISSION_NODE_ButtonLock_buttonlock_op)){
					permissionButtonlockOp = true;
				}else{
					return false;
				}
				
			} else {
				// no Permission installed ! (op only)
				if (!sender.isOp()) {
					return false;
				}else{
					permissionButtonlock = true;
					permissionButtonlockOp = true;
				}
			}

		}else{
			permissionButtonlockOp = true;
		}

		if (args.length == 1 && permissionButtonlockOp) {

			// save all ...
			if (args[0].equalsIgnoreCase("save") || args[0].endsWith("s")) {
				ButtonLock.configFile.write();
				ButtonLock.lockedGroupsFile.write();

				String msg = "All config-files saved ... ";
				if (sender instanceof Player)
					sender.sendMessage(ChatColor.GRAY + msg);
				ButtonLock.log.info(ButtonLock.consoleOutputHeader + " " + msg);
				return true;
			}

			// reload all ...
			if (args[0].equalsIgnoreCase("reload") || args[0].endsWith("rl")) {
				ButtonLock.configFile = new Config();
				ButtonLock.grouplist = new ArrayList<LockedBlockGroup>();

				ButtonLock.configFile.read();
				ButtonLock.lockedGroupsFile.read();

				String msg = "All config-files reloaded ... ";
				if (sender instanceof Player)
					sender.sendMessage(ChatColor.GRAY + msg);
				ButtonLock.log.info(ButtonLock.consoleOutputHeader + " " + msg);
				return true;
			}

			// reset ...
			if (args[0].equalsIgnoreCase("reset")) {
				ButtonLock.configFile = new Config(true);
				ButtonLock.grouplist = new ArrayList<LockedBlockGroup>();
				ButtonLock.configFile.write();
				ButtonLock.lockedGroupsFile.write();

				String msg = ButtonLock.pluginName + " reseted ... ";
				if (sender instanceof Player)
					sender.sendMessage(ChatColor.GRAY + msg);
				ButtonLock.log.info(ButtonLock.consoleOutputHeader + " " + msg);
				return true;
			}
			
				
		}	
			
		if (args.length >= 2 && permissionButtonlockOp) {

				if (args[0].equalsIgnoreCase("protectableBlocks") || args[0].equalsIgnoreCase("protectableBlock") || args[0].equalsIgnoreCase("pab")) {
				
					PlayerVars currentPlayerVars = null;
					if (sender instanceof Player) currentPlayerVars = ButtonLock.getPlayerVars((Player) sender);
					
					ArrayList<Material> materials = new ArrayList<Material>();
					
					if (currentPlayerVars !=  null && args.length == 2) {
						if (currentPlayerVars.getCurrentClickedBlock() != null) {
							materials.add(currentPlayerVars.getCurrentClickedBlock().getType());
						}
					}
					
					if (materials.isEmpty() && args.length == 3){
						for (Material currentMatrial : Material.values()) {
							
							boolean argIsID = true;
							int id = 0;
							try {
								id = Integer.valueOf(args[2]);
							} catch (Exception e) {
								argIsID = false;
							}
						
							if (argIsID) {
								if (currentMatrial.getId() == id){
									materials.add(currentMatrial);
								}
							}else{
								if (currentMatrial.name().toLowerCase().startsWith(args[2].toLowerCase())){
									materials.add(currentMatrial);
								}
							}
						}
					}
					
					if (materials.isEmpty()){
						if (sender instanceof Player) {
							sender.sendMessage(Language.WHICH_BLOCK);
						}else{
							sender.sendMessage(Language.WHICH_MATERIAL_CONSOLE);
						}
						return true;
					}
					
					if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("a") ) {
						for (Material material : materials) {
							if (! ButtonLock.lockableBlocksList.contains(materials)) ButtonLock.lockableBlocksList.add(material);
						}
						
						if (sender instanceof Player) {
							sender.sendMessage(Language.MATERIALS_ADDED + Language.getList(materials.toArray()));
						}else{
							sender.sendMessage(Language.MATERIALS_ADDED_CONSOLE + Language.getList(materials.toArray(), false));
						}
						return true;
					}
				
					if (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("r") ) {
						for (Material material : materials) {
							if (ButtonLock.lockableBlocksList.contains(materials)) ButtonLock.lockableBlocksList.remove(material);
						}
						if (sender instanceof Player) {
							sender.sendMessage(Language.MATERIALS_REMOVED + Language.getList(materials.toArray()));
						}else{
							sender.sendMessage(Language.MATERIALS_REMOVED_CONSOLE + Language.getList(materials.toArray(), false));
						}
						return true;
					}
				}
		}

		

		if (sender instanceof Player) {
			if (args.length >= 2) {
				// reset ...
				if (args[0].equalsIgnoreCase("group") || args[0].equalsIgnoreCase("g") && permissionButtonlock) {

					PlayerVars currentPlayerVars = ButtonLock.getPlayerVars((Player) sender);
					
					if (currentPlayerVars != null) {
						LockedBlockGroup group = currentPlayerVars.getCurrentClickedLockedGroup();
						Block block = currentPlayerVars.getCurrentClickedBlock();

						if (group != null) {
							
							if ( ((group.getProtectionMode() == PROTECTION_MODE.PRIVATE || group.getProtectionMode() == PROTECTION_MODE.PUBLIC) && (! group.isOwner(((Player) sender).getName()))) ) {
								sender.sendMessage(Language.DENIED);
								if (group.getProtectionMode() == PROTECTION_MODE.PRIVATE){
									sender.sendMessage(Language.PROTECTION_MODE_IS + Language.PRIVATE);
									sender.sendMessage(Language.PROTECTION_OWNER_LIST + Language.getList(group.getOwnerList()));
								}
								if (group.getProtectionMode() == PROTECTION_MODE.PUBLIC){
									sender.sendMessage(Language.PROTECTION_MODE_IS + Language.PUBLIC);
									sender.sendMessage(Language.PROTECTION_OWNER_LIST + Language.getList(group.getOwnerList()));
								}
								
								return true;
							}
							
							if (!group.isUnlocked() && currentPlayerVars.getEnteredPasswords() > 0 && group.isForceingEnterPasswordEveryTime() == false) {
								for (int enteresPasswordHashIndex = 0; enteresPasswordHashIndex < currentPlayerVars.getEnteredPasswords(); enteresPasswordHashIndex++) {
									if (LockedBlockGroup.checkPassword(currentPlayerVars,currentPlayerVars.getPassword(enteresPasswordHashIndex))) {
										// currentPlayerVars.getPlayer().sendMessage(Language.SUCCEED);
										break;
									}
								}
							}

						
							if (args.length >= 2) {
								if (args[1].equalsIgnoreCase("costs")
										|| args[1].equalsIgnoreCase("cost")
										|| args[1].equalsIgnoreCase("c")) {
									if (group.isUnlocked()) {
										
										double setting = ButtonLock.configFile.iConomyCosts;
										
										if (args.length == 3) {
											setting = Double.valueOf(args[2]);
										}

										if (setting < 0) setting = 0;

										group.costs = setting;
										group.ChangedSetting_costs = true;
		
										if (setting == 0) {
											sender.sendMessage(Language.GROUP_COSTS_CHANGED_FREE);
										} else {
											sender.sendMessage(Language.GROUP_COSTS_CHANGED_COSTS + setting);
										}
									} else {
										sender.sendMessage(Language.DENIED);
									}
									currentPlayerVars.getCurrentClickedLockedGroup().setUnlock(false);
									return true;
								}
								
							}
							
							if (args.length == 3) {
								if (args[1].equalsIgnoreCase("setlockedstate")
										|| args[1].equalsIgnoreCase("setls")
										|| args[1].equalsIgnoreCase("sls")) {
									if (group.isUnlocked()) {

										LOCKED_STATE setting = null;
										LOCKED_STATE settingBefore = group.getLockedState();
										for (LOCKED_STATE state : LOCKED_STATE.values()) {
											if (state.name().toUpperCase().startsWith(args[2].toUpperCase())) setting = state;
										}

										if (setting != null) {
											if (setting != settingBefore){
												
												if (BlockFunctions.isaDoorAsStateType(block)) {
													if (setting == LOCKED_STATE.ON) setting = LOCKED_STATE.OPEN;
													if (setting == LOCKED_STATE.OFF) setting = LOCKED_STATE.CLOSE;
												}
												
												if (BlockFunctions.isaLeverAsStateType(block)) {
													if (setting == LOCKED_STATE.OPEN) setting = LOCKED_STATE.ON;
													if (setting == LOCKED_STATE.CLOSE) setting = LOCKED_STATE.OFF;
												}
												
												group.setLockedState(setting);
												sender.sendMessage(Language.STATE_CHANGED + setting.name().toLowerCase());
											}
										}else{
											//invalid state ... 
											sender.sendMessage(Language.INVALID_STATE);	
										}
										
									} else {
										sender.sendMessage(Language.DENIED);
									}
									currentPlayerVars.getCurrentClickedLockedGroup().setUnlock(false);
									return true;
								}
								
								if (args[1].equalsIgnoreCase("forcePassword")
										|| args[1].equalsIgnoreCase("forcepw")
										|| args[1].equalsIgnoreCase("fpw")) {
									if (group.isUnlocked()) {
										boolean setting = Boolean.parseBoolean(args[2]);

										boolean settingBefore = group.isForceingEnterPasswordEveryTime();
										if (setting != settingBefore)
											group.changedSetting_forceEnterPasswordEveryTime = true;

										group.setForceEnterPasswordEveryTime(setting);

										if (setting) {
											sender.sendMessage(Language.GROUP_FORCEPW);
										} else {
											sender.sendMessage(Language.GROUP_NOT_FORCEPW);
										}
									} else {
										sender.sendMessage(Language.DENIED);
									}
									currentPlayerVars.getCurrentClickedLockedGroup().setUnlock(false);
									return true;
								}

							}
							
							if (args.length >= 4) {
								if (args[1].equalsIgnoreCase("owner") || args[1].equalsIgnoreCase("o")) {

									if (group.isUnlocked()) {
										
										ArrayList<String> addedOrRemovedPlayerNames = new ArrayList<String>();
										
										if (args[2].equalsIgnoreCase("add") || args[2].equalsIgnoreCase("a")) {
											
											for (int argIndex = 3; argIndex < args.length; argIndex++) {																					
												String name = group.addOwner(args[argIndex]);
												if (! name.isEmpty()) addedOrRemovedPlayerNames.add(name);		
											}
											
											if (addedOrRemovedPlayerNames.size() >= 1){
												sender.sendMessage(Language.PROTECTION_OWNER_ADDED + Language.getList(addedOrRemovedPlayerNames.toArray()));
											}else{
												sender.sendMessage(Language.PLAYER_NOT_FOUND);
											}
																						
										}else if (args[2].equalsIgnoreCase("remove") || args[2].equalsIgnoreCase("r")) {
											
											for (int argIndex = 3; argIndex < args.length; argIndex++) {																					
												String name = group.removeOwner(args[argIndex]);
												if (! name.isEmpty()) addedOrRemovedPlayerNames.add(name);			
											}
											
											if (addedOrRemovedPlayerNames.size() >= 1){
												sender.sendMessage(Language.PROTECTION_OWNER_REMOVED + Language.getList(addedOrRemovedPlayerNames.toArray()));
											}else{
												sender.sendMessage(Language.PLAYER_NOT_FOUND);
											}
										}
										
									} else {
										sender.sendMessage(Language.DENIED);
									}

									currentPlayerVars.getCurrentClickedLockedGroup().setUnlock(false);
									return true;
								}
							}
							
							if (block != null) {
								if (group.isUnlocked()) {
									if (args[1].equalsIgnoreCase("add")
											|| args[1].equalsIgnoreCase("a")) {
										currentPlayerVars.addNextclickedBlock = group;
										sender.sendMessage(Language.GROUP_BLOCK_ADD);
										currentPlayerVars.getCurrentClickedLockedGroup().setUnlock(false);
										return true;
									}

									if (args[1].equalsIgnoreCase("remove")
											|| args[1].equalsIgnoreCase("r")) {
										currentPlayerVars.removeNextclickedBlock = group;
										sender.sendMessage(Language.GROUP_BLOCK_REMOVE);
										currentPlayerVars.getCurrentClickedLockedGroup().setUnlock(false);
										return true;
									}
								} else {
									sender.sendMessage(Language.DENIED);
									currentPlayerVars.getCurrentClickedLockedGroup().setUnlock(false);
									return true;
								}

							} else {
								sender.sendMessage(Language.WHICH_BLOCK + "A");
								currentPlayerVars
										.getCurrentClickedLockedGroup()
										.setUnlock(false);
								return true;
							}
						}else{
							sender.sendMessage(Language.WHICH_BLOCK);
							return true;
						}
					} else {
						sender.sendMessage(Language.WHICH_BLOCK);
						return true;
					}
				}
			}
		}

		return false;
	}

}
