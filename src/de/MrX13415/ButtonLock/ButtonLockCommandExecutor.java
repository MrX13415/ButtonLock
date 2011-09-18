package de.MrX13415.ButtonLock;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ButtonLockCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {

			if (ButtonLock.permissionHandler != null
					&& ButtonLock.configFile.usePermissions) {
				// use Permission
				if (!ButtonLock.permissionHandler.permission((Player) sender,
						ButtonLock.PERMISSION_NODE_ButtonLock_buttonlock)) {
					return false;
				}
			} else {
				// no Permission installed ! (op only)
				if (!sender.isOp()) {
					return false;
				}
			}

		}

		if (args.length == 1) {

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
				ButtonLock.configFile = new Config();
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

		if (sender instanceof Player) {
			if (args.length >= 2) {
				// reset ...
				if (args[0].equalsIgnoreCase("group")
						|| args[0].equalsIgnoreCase("g")) {

					PlayerVars currentPlayerVars = ButtonLock
							.getPlayerVars((Player) sender);
					if (currentPlayerVars != null) {
						LockedBlockGroup group = currentPlayerVars
								.getCurrentClickedLockedGroup();
						Block block = currentPlayerVars
								.getCurrentClickedBlock();

						if (group != null) {
							if (!group.isUnlocked()
									&& currentPlayerVars.getEnteredPasswords() > 0
									&& group.isForceingEnterPasswordEveryTime() == false) {
								for (int enteresPasswordHashIndex = 0; enteresPasswordHashIndex < currentPlayerVars
										.getEnteredPasswords(); enteresPasswordHashIndex++) {
									if (LockedBlockGroup
											.checkPassword(
													currentPlayerVars,
													currentPlayerVars
															.getPassword(enteresPasswordHashIndex))) {
										// currentPlayerVars.getPlayer().sendMessage(Language.TEXT_SUCCEED);
										break;
									}
								}
							}

							if (args.length == 3) {

								if (args[1].equalsIgnoreCase("forcePassword")
										|| args[1].equalsIgnoreCase("forcepw")
										|| args[1].equalsIgnoreCase("fpw")) {
									if (group.isUnlocked()) {
										boolean setting = Boolean
												.parseBoolean(args[2]);

										boolean settingBefore = group
												.isForceingEnterPasswordEveryTime();
										if (setting != settingBefore)
											group.changedSetting_forceEnterPasswordEveryTime = true;

										group.setForceEnterPasswordEveryTime(setting);

										if (setting) {
											sender.sendMessage(Language.TEXT_GROUP_FORCEPW);
										} else {
											sender.sendMessage(Language.TEXT_GROUP_NOT_FORCEPW);
										}
									} else {
										sender.sendMessage(Language.TEXT_DENIED);
									}
									currentPlayerVars
											.getCurrentClickedLockedGroup()
											.setUnlock(false);
									return true;
								}

								if (args[1].equalsIgnoreCase("costs")
										|| args[1].equalsIgnoreCase("cost")
										|| args[1].equalsIgnoreCase("c")) {
									if (group.isUnlocked()) {
										double setting = Double
												.valueOf(args[2]);

										if (setting < 0)
											setting = 0;

										group.costs = setting;
										group.ChangedSetting_costs = true;

										if (setting == 0) {
											sender.sendMessage(Language.TEXT_GROUP_COSTS_CHANGED_FREE);
										} else {
											sender.sendMessage(Language.TEXT_GROUP_COSTS_CHANGED_COSTS + setting);
										}
									} else {
										sender.sendMessage(Language.TEXT_DENIED);
									}
									currentPlayerVars
											.getCurrentClickedLockedGroup()
											.setUnlock(false);
									return true;
								}

							}

							if (block != null) {
								if (group.isUnlocked()) {
									if (args[1].equalsIgnoreCase("add")
											|| args[1].equalsIgnoreCase("a")) {
										currentPlayerVars.addNextclickedBlock = group;
										sender.sendMessage(Language.TEXT_GROUP_BLOCK_ADD);
										currentPlayerVars
												.getCurrentClickedLockedGroup()
												.setUnlock(false);
										return true;
									}

									if (args[1].equalsIgnoreCase("remove")
											|| args[1].equalsIgnoreCase("r")) {
										currentPlayerVars.removeNextclickedBlock = group;
										sender.sendMessage(Language.TEXT_GROUP_BLOCK_REMOVE);
										currentPlayerVars
												.getCurrentClickedLockedGroup()
												.setUnlock(false);
										return true;
									}
								} else {
									sender.sendMessage(Language.TEXT_DENIED);
									currentPlayerVars
											.getCurrentClickedLockedGroup()
											.setUnlock(false);
									return true;
								}

							} else {
								sender.sendMessage(Language.TEXT_WHICH_BLOCK);
								currentPlayerVars
										.getCurrentClickedLockedGroup()
										.setUnlock(false);
								return true;
							}
						}

					} else {
						sender.sendMessage(Language.TEXT_WHICH_BLOCK);
						return true;
					}
				}
			}
		}

		return false;
	}

}
