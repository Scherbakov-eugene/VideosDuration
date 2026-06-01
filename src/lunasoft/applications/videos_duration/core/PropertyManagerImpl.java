package lunasoft.applications.videos_duration.core;

import lunasoft.common.util.PropertyManager;

public class PropertyManagerImpl extends PropertyManager
{
	public String getFilenameMessages()
	{
		return "Videos duration.messages";
	}

	public String getFilenameProperties()
	{
		return "Videos duration.properties";
	}

	// Properties.
	public static final String PROPERTY_LAST_OPENED_DIRECTORY = "PROPERTY_LAST_OPENED_DIRECTORY";
	public static final String PROPERTY_FAVORITES_COUNT = "PROPERTY_FAVORITES_COUNT";
	public static final String PROPERTY_FAVORITE_PATH = "PROPERTY_FAVORITE_PATH_";
	public static final String PROPERTY_PATH_TO_MEDIA_INFO = "PROPERTY_PATH_TO_MEDIA_INFO";

	// Messages / Menu.
	public static final String MSG_MENU_FILE = "menu.file";
	public static final String MSG_MENU_FILE_EXIT = "menu.file.exit";
	public static final String MSG_MENU_OPTIONS = "menu.options";
	public static final String MSG_MENU_OPTIONS_SETTINGS = "menu.options.settings";
	public static final String MSG_MENU_HELP = "menu.help";
	public static final String MSG_MENU_HELP_HELP_TOPICS = "menu.help.help.topics";
	public static final String MSG_MENU_HELP_ABOUT = "menu.help.about";

	// Messages.
	public static final String MSG_PATH_TO_VIDEOS = "path.to.videos";
	public static final String MSG_VIDEOS = "videos";
	public static final String MSG_TOTAL = "total";
	public static final String MSG_BROWSE = "browse";
	public static final String MSG_OPEN_CATALOG = "open.catalog";
	public static final String MSG_CATALOGS = "catalogs";
	public static final String MSG_OK = "ok";
	public static final String MSG_CANCEL = "cancel";
	public static final String MSG_PATH = "path";
	public static final String MSG_DATE = "date";
	public static final String MSG_FAVORITES = "favorites";
	public static final String MSG_UP = "up";
	public static final String MSG_ADD = "add";
	public static final String MSG_CHANGE = "change";
	public static final String MSG_DELETE = "delete";
	public static final String MSG_MOVE_UP = "move.up";
	public static final String MSG_MOVE_DOWN = "move.down";
	public static final String MSG_DO_YOU_REALLY_WANT_TO_DELETE_SELECTED_RECORDS = "do.you.really.want.to.delete.selected.records";
	public static final String MSG_MOVE = "move";
	public static final String MSG_SELECT_ONLY_ONE_ROW = "select.only.one.row";
	public static final String MSG_SELECTED_PATH_IS_NOT_A_DIRECTORY = "selected.path.is.not.a.directory";
	public static final String MSG_ERROR = "error";
	public static final String MSG_PARENT_DIR_IS_NOT_A_DIRECTORY = "parent.dir.is.not.a.directory";
	public static final String MSG_OPTIONS = "options";
	public static final String MSG_SETTINGS = "settings";
	public static final String MSG_PATH_TO_MEDIA_INFO = "path.to.media.info";
	public static final String MSG_CLOSE = "close";
	public static final String MSG_SELECT_ALL = "select.all";
	public static final String MSG_DESELECT_ALL = "deselect.all";
	public static final String MSG_DURATION = "duration";
	public static final String MSG_PROGRESS = "progress";
	public static final String MSG_CURRENT_FILE = "current.file";
	public static final String MSG_FILES_PROCESSED = "files.processed";
	public static final String MSG_FILES_SELECTED = "files.selected";
	public static final String MSG_SCANNING = "scanning";
}
