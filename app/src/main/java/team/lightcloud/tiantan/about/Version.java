/*
 * TianTan (天探)
 * Copyright (C) 2022  Astronomy Group, Class 1 Senior 1, Wujiang High School (吴江中学（原）高一（1）班天文小组)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package team.lightcloud.tiantan.about;

public class Version {
	public String versionName;
	public int versionCode;
	public String versionDescription;

	public Version(String name, int code, String description){
		versionName = name;
		versionCode = code;
		versionDescription = description;
	}
}
