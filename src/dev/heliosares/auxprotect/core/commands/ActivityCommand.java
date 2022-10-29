package dev.heliosares.auxprotect.core.commands;

import java.util.List;
import dev.heliosares.auxprotect.adapters.SenderAdapter;
import dev.heliosares.auxprotect.core.APPermission;
import dev.heliosares.auxprotect.core.Command;
import dev.heliosares.auxprotect.core.IAuxProtect;

import dev.heliosares.auxprotect.core.Language;
public class ActivityCommand extends Command {

	public ActivityCommand(IAuxProtect plugin) {
		super(plugin, "activity", APPermission.LOOKUP_ACTIVITY);
	}

	@Override
	public void onCommand(SenderAdapter sender, String label, String[] args) {
		if (args.length != 2 && args.length != 3) {
			sender.sendLang(Language.L.INVALID_SYNTAX);
			return;
		}
		String cmd = plugin.getCommandPrefix()
				+ String.format(" lookup #activity user:%s action:activity time:", args[1]);
		if (args.length > 2) {
			cmd += args[2];
		} else {
			cmd += "2h";
		}
		sender.executeCommand(cmd);
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public List<String> onTabComplete(SenderAdapter sender, String label, String[] args) {
		return APCommand.tabCompletePlayerAndTime(plugin, sender, label, args);
	}
}