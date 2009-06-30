/*
 * Preference.java
 *
 * Created on June 29, 2009, 1:52:15 PM
 *
 * This file is a part of Shoddy Battle.
 * Copyright (C) 2009  Catherine Fitzpatrick and Benjamin Gwin
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, visit the Free Software Foundation, Inc.
 * online at http://gnu.org.
 */

package shoddybattleclient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;

/**
 *
 * @author ben
 */
public class Preference {
    private static final Preferences m_prefs = Preferences.userRoot();

    private static final String USER_HEALTH_DISPLAY = "userHealthDisplay";
    private static final String OPPONENT_HEALTH_DISPLAY = "opponentHealthDisplay";
    private static final String TIME_STAMPS_ENABLED = "timeStampsEnabled";
    private static final String TIME_STAMP_FORMAT = "timeStampFormat";
    private static final String IGNORED_USERS = "ignoredUsers";

    public static enum HealthDisplay {
        EXACT ("Exact"),
        PERCENT ("Percent"),
        BOTH ("Both");
        private String m_str;
        HealthDisplay(String str) { m_str = str; }
        public String toString() { return m_str; }
    }

    public static void setUserHealthDisplay(HealthDisplay disp) {
        m_prefs.putInt(USER_HEALTH_DISPLAY, disp.ordinal());
    }
    public static void setOpponentHealthDisplay(HealthDisplay disp) {
        m_prefs.putInt(OPPONENT_HEALTH_DISPLAY, disp.ordinal());
    }
    public static HealthDisplay getHealthDisplay(boolean us) {
        String key;
        HealthDisplay def;
        if (us) {
            key = USER_HEALTH_DISPLAY;
            def = HealthDisplay.BOTH;
        } else {
            key = OPPONENT_HEALTH_DISPLAY;
            def = HealthDisplay.PERCENT;
        }
        return HealthDisplay.values()[m_prefs.getInt(key, def.ordinal())];
    }

    public static void setTimeStampsEnabled(boolean enabled) {
        m_prefs.putBoolean(TIME_STAMPS_ENABLED, enabled);
    }
    public static boolean timeStampsEnabled() {
        return m_prefs.getBoolean(TIME_STAMPS_ENABLED, false);
    }
    public static void setTimeStampFormat(String format) {
        m_prefs.put(TIME_STAMP_FORMAT, format);
    }
    public static String getTimeStampFormat() {
        return m_prefs.get(TIME_STAMP_FORMAT, "[h:mm:ss]  ");
    }
    public static void setIgnoredUsers(String users) {
        m_prefs.put(IGNORED_USERS, users);
    }
    public static void setIgnoredUsers(List<String> names) {
        StringBuilder builder = new StringBuilder();
        for (String s : names) {
            if (!s.equals("")) {
                builder.append(s);
                builder.append(",");
            }
        }
        if (builder.length() > 0) builder.deleteCharAt(builder.length() - 1);
        setIgnoredUsers(builder.toString());
    }
    public static boolean ignore(String user) {
        List<String> names = getIgnoredUsers();
        boolean success = true;
        for (String s : names) {
            if (s.equalsIgnoreCase(user)) {
                success = false;
                break;
            }
        }
        if (success) {
            names.add(user);
            setIgnoredUsers(names);
        }
        return success;
    }
    public static boolean unignore(String user) {
        List<String> names = getIgnoredUsers();
        boolean success = false;
        for (String s : names) {
            if (s.equalsIgnoreCase(user)) {
                success = true;
                names.remove(s);
                break;
            }
        }
        if (success) {
            setIgnoredUsers(names);
        }
        return success;
    }
    public static List<String> getIgnoredUsers() {
        List<String> ret = new ArrayList<String>();
        Set<String> set = new HashSet<String>();
        String[] users = m_prefs.get(IGNORED_USERS, "").split(",");
        for (int i = 0; i < users.length; i++) {
            String name = users[i];
            if (!name.equals("") && !set.contains(name)) {
                name = name.trim();
                ret.add(name);
                set.add(name);
            }
        }
        return ret;
    }
    public static String getIgnoredUsersStr() {
        return m_prefs.get(IGNORED_USERS, "");
    }
    public static boolean ignoring(String user) {
        List<String> users = getIgnoredUsers();
        for (String s : users) {
            if (s.equalsIgnoreCase(user)) return true;
        }
        return false;
    }
}