package dev.heliosares.auxprotect.core;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import dev.heliosares.auxprotect.adapters.ConfigAdapter;
import dev.heliosares.auxprotect.utils.ColorTranslate;

public class Language {
	private static Supplier<ConfigAdapter> langSupplier;
	private static IAuxProtect plugin;
	private static ConfigAdapter lang;
	private static String c1;
	private static String c2;
	private static String c3;

	public static void load(IAuxProtect plugin, Supplier<ConfigAdapter> langSupplier) throws IOException {
		Language.plugin = plugin;
		Language.langSupplier = langSupplier;
		reload();
	}

	public static void reload() throws IOException {
		lang = langSupplier.get();
		lang.load();

		if (lang != null) {
			c1 = "&" + lang.getString("color.p");
			c2 = "&" + lang.getString("color.s");
			c3 = "&" + lang.getString("color.t");
		}
	}

	public static String getLocale() {
		try {
			return lang.getFile().getName().split("\\.")[0];
		} catch (Exception e) {
			return null;
		}
	}

	public static String translate(L l, Object... format) {
		return l.translate(format);
	}

	public static enum L {
		UPDATE, //
		NO_PERMISSION, //
		NO_PERMISSION_NODE, //
		NO_PERMISSION_FLAG, //
		INVALID_PARAMETER, //
		INVALID_SYNTAX, //
		INVALID_NOTENOUGH, //
		ERROR, //
		ACTION_DISABLED, //
		NOTPLAYERERROR, //
		COMMAND__LOOKUP__UNKNOWN_WORLD, //
		COMMAND__LOOKUP__LOOKING, //
		COMMAND__LOOKUP__NORESULTS, //
		COMMAND__LOOKUP__COUNT, //
		COMMAND__LOOKUP__PAGE_FOOTER, //
		COMMAND__LOOKUP__NO_RESULTS_SELECTED, //
		COMMAND__LOOKUP__NOPAGE, //
		COMMAND__LOOKUP__TOOMANY, //
		COMMAND__LOOKUP__INCOMPATIBLE_TABLES, //
		COMMAND__LOOKUP__ACTION_NEGATE, //
		COMMAND__LOOKUP__ACTION_PERM, //
		COMMAND__LOOKUP__ACTION_NONE, //
		COMMAND__LOOKUP__RATING_WRONG, //
		COMMAND__LOOKUP__NODATA, //
		WATCH_NONE, //
		WATCH_ING, //
		WATCH_REMOVED, //
		WATCH_NOW, //
		PURGE_PURGING, //
		PURGE_UIDS, //
		PURGE_VACUUM, //
		PURGE_COMPLETE, //
		PURGE_ERROR, //
		PURGE_TIME, //
		PURGE_TABLE, //
		BACKUP_SQLITEONLY, //
		UNKNOWN_SUBCOMMAND, //
		COMMAND__HELP, //
		LOOKUP_PLAYTIME_NOUSER, //
		LOOKUP_PLAYTIME_TOOMANYUSERS, //
		LOOKUP_PLAYERNOTFOUND, //
		LOOKUP_UNKNOWNACTION, //
		PLAYTIME_TOOMANYUSERS, //
		PLAYTIME_NOUSER, //
		XRAY_RATE_NOCHANGE, //
		XRAY_RATE_WRITTEN, //
		XRAY_DONE, //
		XRAY_NOTFOUND, //
		XRAY_TOOMANY, //
		XRAY_ALREADY_RATED, //
		XRAY_CLICK_TO_CHANGE, //
		INACTIVE_ALERT, //
//		YES, //
//		NO, //
		INV_MANUAL_SUCCESS, //
		INV_TOOSOON, //
		DATABASE_BUSY, //
		ACTIONS,//
		;

		public final String name;

		L() {
			name = super.toString().replace("__", ".").replace("_", "-").toLowerCase();
		}

		@Override
		public String toString() {
			return name;
		}

		public String translate(Object... format) {
			return translateSubcategory(null, format);
		}

		public String translateSubcategory(String subcategory, Object... format) {
			String message = null;
			String name = toString();
			if (subcategory != null && subcategory.length() > 0) {
				name += "." + subcategory.toLowerCase();
			}
			if (lang != null && !lang.isNull()) {
				message = lang.getString(name);
			}
			if (message == null) {
				String out = "[lang:" + name;
				for (Object part : format) {
					out += ", " + part;
				}
				return out + "]";
			}
			message = convert(message);
			if (format == null || format.length == 0) {
				return message;
			}
			return String.format(message, format);
		}

		public List<String> translateList() {
			return translateSubcategoryList(null);
		}

		public List<String> translateSubcategoryList(String subcategory) {
			List<String> message = null;
			String name = toString();
			if (subcategory != null && subcategory.length() > 0) {
				name += "." + subcategory.toLowerCase();
			}
			if (lang != null && !lang.isNull()) {
				message = lang.getStringList(name);
			}
			if (message == null || message.size() == 0) {
				return null;
			}
			return message.stream().map(s -> convert(s)).collect(Collectors.toList());
		}
	}

	public static String convert(String s) {
		if (c1 != null)
			s = s.replace("&p", c1);
		if (c2 != null)
			s = s.replace("&s", c2);
		if (c3 != null)
			s = s.replace("&t", c3);
		s = s.replace("$prefix", plugin.getCommandAlias());
		return ColorTranslate.cc(s);
	}
}