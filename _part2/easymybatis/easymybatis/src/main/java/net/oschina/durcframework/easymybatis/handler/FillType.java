/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.oschina.durcframework.easymybatis.handler;

/*
 * 权限控制算法(位与算)
 */
class Opt {
	// 操作码
	public static final int SELECT_RIGHT = 1;
	public static final int INSERT_RIGHT = 2;
	public static final int UPDATE_RIGHT = 3;

	// 权限值
	public static final int SELECT_POWER = (int) Math.pow(2, SELECT_RIGHT);
	public static final int INSERT_POWER = (int) Math.pow(2, INSERT_RIGHT);
	public static final int UPDATE_POWER = (int) Math.pow(2, UPDATE_RIGHT);
}

/**
 * 字段填充类型. <br>
 * 字段插入后不能修改用FillType.INSERT，如：create_time字段<br>
 * 字段插入后能修改用FillType.UPDATE，如：update_time字段
 */
public enum FillType {
	SELECT(Opt.SELECT_RIGHT, Opt.SELECT_POWER), 
	INSERT(Opt.INSERT_RIGHT, Opt.INSERT_POWER + Opt.SELECT_POWER), 
	UPDATE(Opt.UPDATE_RIGHT, Opt.SELECT_POWER + Opt.INSERT_POWER + Opt.UPDATE_POWER)
	;

	private int right;
	private int power;

	FillType(int right, int power) {
		this.right = right;
		this.power = power;
	}

	public static boolean checkPower(FillType power, FillType right) {
		return checkPower(power.power, right.right);
	}

	/**
	 * 检查是否有权限
	 * 
	 * @param power
	 *            总权限
	 * @param right
	 *            当前操作权限
	 * @return
	 */
	private static boolean checkPower(int power, int right) {
		int purview = (int) Math.pow(2, right);
		return (power & purview) == purview;
	}

	/*
	 * public static void main(String[] args) {
	 * System.out.println(checkPower(FillType.SELECT, FillType.SELECT));
	 * System.out.println(checkPower(FillType.INSERT, FillType.INSERT));
	 * System.out.println(checkPower(FillType.UPDATE, FillType.INSERT));
	 * System.out.println(checkPower(FillType.UPDATE, FillType.SELECT));
	 * System.out.println(checkPower(FillType.INSERT, FillType.UPDATE)); }
	 */

}
