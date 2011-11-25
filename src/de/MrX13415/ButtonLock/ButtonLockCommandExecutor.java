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
			
			if (ButtonLock.permissionHandler != null && ButtonLock.configFile.usePermissions) {
				// use Permission
				if (ButtonLock.permissionHandler.permission((Player) sender, ButtonLock.PERMISSION_NODE_ButtonLock_buttonlock)){
					permissionButtonlock = true;
				}else{
					return false;
				}
				
				if (ButtonLock.permissionHandler.permission((Player) sender, ButtonLock.PERMISSION_NODE_ButtonLock_buttonlock_op)){
					permissionButtonlock = true;
					permissionButtonlockOp = true;
				}else{
					return false;
				}
			
				if (! permissionButtonlock && ! permissionButtonlockOp){
					sender.sendMessage(ButtonLock.language.DENIED);
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

		if (args.length == 1) {
			// version ...
			if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("v")) {
				
				String msg = ButtonLock.pdfFile.getName() + " " + ButtonLock.pdfFile.getVersion() + " " + ButtonLock.pdfFile.getAuthors();
				if (sender instanceof Player) {
					msg = ChatColor.RED + ButtonLock.pdfFile.getName() + " " + ChatColor.GRAY + ButtonLock.pdfFile.getVersion() + " " + ChatColor.GOLD + ButtonLock.pdfFile.getAuthors();
				}
				
				sender.sendMessage(msg);
				return true;
			}
		}
		
		if (args.length == 1 && permissionButtonlockOp) {

			// save all ...
			if (args[0].equalsIgnoreCase("debug")) {
				
				ButtonLock.debugmode = ! ButtonLock.debugmode;
				
				if (ButtonLock.debugmode == true) {
					if (sender instanceof Player)
						sender.sendMessage(ChatColor.GOLD + ButtonLock.consoleOutputHeader + ChatColor.GRAY + " Debug mode" + ChatColor.GOLD + " enabled");
					ButtonLock.log.info(ButtonLock.consoleOutputHeader + " Debug mode enabled");
				}else{
					if (sender instanceof Player)
						sender.sendMessage(ChatColor.GOLD + ButtonLock.consoleOutputHeader + ChatColor.GRAY + " Debug mode" + ChatColor.GOLD + " disabled");
					ButtonLock.log.info(ButtonLock.consoleOutputHeader + " Debug mode disabled");	
				}
				return true;
			}
			
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

			// remove a locked group ...
			if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("r")) {
				if (sender instanceof Player){
					PlayerVars currentVars = ButtonLock.getPlayerVars((Player) sender);
					
					if (currentVars != null) {
						LockedBlockGroup group = currentVars.getCurrentClickedLockedGroup();
						
						if (group != null) {
							ButtonLock.removeLockedBlock(group);
							sender.sendMessage(ButtonLock.language.PROTECTION_REMOVED);
						}else{
							sender.sendMessage(ButtonLock.language.WHICH_BLOCK);
						}
					}
				}else{
					sender.sendMessage(String.format(ButtonLock.language.COMMAND_INGAME_ONLY, ButtonLock.consoleOutputHeader));
				}
				return true;
			}
				
		}	
		
		if (args.length == 2 && permissionButtonlockOp) {

			// reset ...
			if (args[0].equalsIgnoreCase("reset")) {
				
				boolean all = false;
				boolean langs = false;
				boolean config = false;
				boolean groups = false;
							
				if (args[1].equalsIgnoreCase("all")) all = true;
				if (args[1].equalsIgnoreCase("lang") || args[1].equalsIgnoreCase("langs")) langs = true;
				if (args[1].equalsIgnoreCase("config")) config = true;
				if (args[1].equalsIgnoreCase("group") || args[1].equalsIgnoreCase("groups")) groups = true;
				
				if (langs || all) {
					//reset language ...
					ButtonLock.updateLanguages(true);
					
					String msg = ButtonLock.consoleOutputHeader + " Languages reseted ...";
					if (sender instanceof Player) sender.sendMessage(ChatColor.GRAY + msg);
					ButtonLock.log.info(msg);
					
				}
			
				if (config || all) {
					//reset config ...
					ButtonLock.configFile = new Config();
					ButtonLock.configFile.update(true);
					
					String msg = ButtonLock.consoleOutputHeader + " Config reseted ... ";
					if (sender instanceof Player) sender.sendMessage(ChatColor.GRAY + msg);
					ButtonLock.log.info(msg);
					
				}
				
				if (groups || all) {
					//reset grouplist ...
					ButtonLock.grouplist = new ArrayList<LockedBlockGroup>();
					ButtonLock.lockedGroupsFile.write();

					String msg = ButtonLock.consoleOutputHeader + " Locked Groups reseted ... ";
					if (sender instanceof Player) sender.sendMessage(ChatColor.GRAY + msg);
					ButtonLock.log.info(msg);
					
				}
				
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
							
							String[] material = new String[2];
							if(args[2].contains(":")){
								material = args[2].split(":");
							}else if(args[2].contains("-")){
								material = args[2].split("-");
							}else{
								material[0] = args[2];
								material[1] = "0";
							}
							
							boolean argIsID = true;
							
							int id = 0;
							
							
							@SuppressWarnings("unused")
							int data = 0;
							try {
								id = Integer.valueOf(material[0]);
								data = Integer.valueOf(material[1]);
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
							sender.sendMessage(ButtonLock.language.WHICH_BLOCK);
						}else{
							sender.sendMessage(String.format(ButtonLock.language.CONSOLE_WHICH_MATERIAL, ButtonLock.consoleOutputHeader));
						}
						return true;
					}
					
					if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("a") ) {
						for (Material material : materials) {
							if (! ButtonLock.lockableBlocksList.contains(materials)) ButtonLock.lockableBlocksList.add(material);
						}
						
						if (sender instanceof Player) {
							sender.sendMessage(String.format(ButtonLock.language.MATERIALS_ADDED, ButtonLock.language.getList(materials.toArray())));
						}else{
							sender.sendMessage(String.format(ButtonLock.language.CONSOLE_MATERIALS_ADDED, ButtonLock.consoleOutputHeader, ButtonLock.language.getList(materials.toArray(), false)));
						}
						return true;
					}
				
					if (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("r") ) {
						for (Material material : materials) {
							if (ButtonLock.lockableBlocksList.contains(materials)) ButtonLock.lockableBlocksList.remove(material);
						}
						if (sender instanceof Player) {
							sender.sendMessage(String.format(ButtonLock.language.MATERIALS_REMOVED, ButtonLock.language.getList(materials.toArray())));
						}else{
							sender.sendMessage(String.format(ButtonLock.language.CONSOLE_MATERIALS_REMOVED,ButtonLock.consoleOutputHeader, ButtonLock.language.getList(materials.toArray(), false)));
						}
						return true;
					}
				}
		}

		

		if (sender instanceof Player) {
			if (args.length == 1) {
			
				if (args[0].equalsIgnoreCase("information") || args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("i")) {

					PlayerVars currentPlayerVars = ButtonLock.getPlayerVars((Player) sender);
					
					if (currentPlayerVars != null) {
						LockedBlockGroup group = currentPlayerVars.getCurrentClickedLockedGroup();
						Block block = currentPlayerVars.getCurrentClickedBlock();
		
						sender.sendMessage(ButtonLock.language.GROUP_INFO);
		
						ArrayList<Material> materials = new ArrayList<Material>();
						
						if (group != null) {
							String status = ButtonLock.language.LOCKED;
							String protectionMode = ButtonLock.language.PASSWORD;
							String costs = String.format(ButtonLock.language.COSTS, group.costs);
							
							if (group.costs == 0 && ButtonLock.configFile.iConomyIsFreeAsDefault == false) costs = ButtonLock.language.COSTS_FREE;
							if (group.isUnlocked()) status = ButtonLock.language.UNLOCKED;
							if (group.getProtectionMode() == PROTECTION_MODE.PRIVATE) protectionMode = ButtonLock.language.PRIVATE;
							if (group.getProtectionMode() == PROTECTION_MODE.PUBLIC) protectionMode = ButtonLock.language.PUBLIC;
							
							sender.sendMessage(String.format(ButtonLock.language.PROTECTION_MODE_IS, protectionMode));
							sender.sendMessage(String.format(ButtonLock.language.STATUS, status));
							sender.sendMessage(costs);
							sender.sendMessage(String.format(ButtonLock.language.PROTECTION_OWNER_LIST, ButtonLock.language.getList(group.getOwnerList())));
							sender.sendMessage(String.format(ButtonLock.language.ONE_TIME_PASSWORDS, group.getOneTimePasswords()));
							sender.sendMessage(String.format(ButtonLock.language.GROUP_SIZE, "" + group.getGroupSize()));
							
							for (int blockIndex = 0; blockIndex < group.getGroupSize(); blockIndex++) {
								materials.add(group.getBlock(blockIndex).getType());
							}
						}else if (block != null){
							Boolean protectable = ButtonLock.isProtectable(block);
							
							sender.sendMessage(String.format(ButtonLock.language.PROTECTABLE, ButtonLock.language.getBoolean(protectable)));
							
							materials.add(block.getType());
						}

						sender.sendMessage(String.format(ButtonLock.language.MATERIAL, ButtonLock.language.getList(materials.toArray())));
						
						sender.sendMessage(ButtonLock.language.GROUP_INFO_ENDE);
						return true;
					}
				}
			}
			
			if (args.length >= 2) {
				
				if (args[0].equalsIgnoreCase("group") || args[0].equalsIgnoreCase("g") && permissionButtonlock) {

					PlayerVars currentPlayerVars = ButtonLock.getPlayerVars((Player) sender);
					
					if (currentPlayerVars != null) {
						LockedBlockGroup group = currentPlayerVars.getCurrentClickedLockedGroup();
						Block block = currentPlayerVars.getCurrentClickedBlock();
						
						if (group != null) {
							
							if (! group.hasAccess((Player) sender)) {
								sender.sendMessage(ButtonLock.language.DENIED);
								return true;
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
											sender.sendMessage(ButtonLock.language.GROUP_COSTS_CHANGED_FREE);
										} else {
											sender.sendMessage(String.format(ButtonLock.language.GROUP_COSTS_CHANGED_COSTS, setting));
										}
									} else {
										sender.sendMessage(ButtonLock.language.DENIED);
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
												sender.sendMessage(String.format(ButtonLock.language.STATE_CHANGED, setting.name().toLowerCase()));
											}
										}else{
											//invalid state ... 
											sender.sendMessage(ButtonLock.language.INVALID_STATE);	
										}
										
									} else {
										sender.sendMessage(ButtonLock.language.DENIED);
									}
									currentPlayerVars.getCurrentClickedLockedGroup().setUnlock(false);
									return true;
								}
								
								if (args[1].equalsIgnoreCase("forcePassword")
										|| args[1].equalsIgnoreCase("forcepw")
										|| args[1].equalsIgnoreCase("fpw")) {
									if (group.isUnlocked()) {
										boolean setting = Boolean.parseBoolean(args[2]);

										System.out.println(args[2] + " " + ButtonLock.language.TRUE + " " + args[2].equalsIgnoreCase(ButtonLock.language.TRUE));
										if(args[2].equalsIgnoreCase(ButtonLock.language.FALSE)) setting = false;
										if(args[2].equalsIgnoreCase(ButtonLock.language.TRUE)) setting = true;
										if(args[2].equalsIgnoreCase("0")) setting = false;
										if(args[2].equalsIgnoreCase("1")) setting = true;
										
										boolean settingBefore = group.isForceingEnterPasswordEveryTime();
										if (setting != settingBefore)
											group.changedSetting_forceEnterPasswordEveryTime = true;

										group.setForceEnterPasswordEveryTime(setting);

										if (setting) {
											sender.sendMessage(ButtonLock.language.GROUP_FORCEPW);
										} else {
											sender.sendMessage(ButtonLock.language.GROUP_NOT_FORCEPW);
										}
									} else {
										sender.sendMessage(ButtonLock.language.DENIED);
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
												sender.sendMessage(String.format(ButtonLock.language.PROTECTION_OWNER_ADDED, ButtonLock.language.getList(addedOrRemovedPlayerNames.toArray())));
											}else{
												sender.sendMessage(ButtonLock.language.PLAYER_NOT_FOUND);
											}
																						
										}else if (args[2].equalsIgnoreCase("remove") || args[2].equalsIgnoreCase("r")) {
											
											for (int argIndex = 3; argIndex < args.length; argIndex++) {																					
												String name = group.removeOwner(args[argIndex]);
												if (! name.isEmpty()) addedOrRemovedPlayerNames.add(name);			
											}
											
											if (addedOrRemovedPlayerNames.size() >= 1){
												sender.sendMessage(String.format(ButtonLock.language.PROTECTION_OWNER_REMOVED, ButtonLock.language.getList(addedOrRemovedPlayerNames.toArray())));
											}else{
												sender.sendMessage(ButtonLock.language.PLAYER_NOT_FOUND);
											}
										}
										
									} else {
										sender.sendMessage(ButtonLock.language.DENIED);
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
										sender.sendMessage(ButtonLock.language.GROUP_BLOCK_ADD);
										currentPlayerVars.getCurrentClickedLockedGroup().setUnlock(false);
										return true;
									}

									if (args[1].equalsIgnoreCase("remove")
											|| args[1].equalsIgnoreCase("r")) {
										currentPlayerVars.removeNextclickedBlock = group;
										sender.sendMessage(ButtonLock.language.GROUP_BLOCK_REMOVE);
										currentPlayerVars.getCurrentClickedLockedGroup().setUnlock(false);
										return true;
									}
								} else {
									sender.sendMessage(ButtonLock.language.DENIED);
									currentPlayerVars.getCurrentClickedLockedGroup().setUnlock(false);
									return true;
								}

							} else {
								sender.sendMessage(ButtonLock.language.WHICH_BLOCK);
								currentPlayerVars
										.getCurrentClickedLockedGroup()
										.setUnlock(false);
								return true;
							}
						}else{
							sender.sendMessage(ButtonLock.language.WHICH_BLOCK);
							return true;
						}
					} else {
						sender.sendMessage(ButtonLock.language.WHICH_BLOCK);
						return true;
					}
				}
			}
		}else{
			sender.sendMessage(String.format(ButtonLock.language.COMMAND_INGAME_ONLY, ButtonLock.consoleOutputHeader));
		}

		return false;
	}

}
