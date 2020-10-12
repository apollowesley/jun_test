/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-9-27 下午6:42:42
 */
package com.absir.system.test.configure;

import com.absir.appserv.configure.xls.XlsBase;
import com.absir.appserv.system.bean.value.JaLang;

/**
 * @author absir
 * 
 */
// @JaEntity(permissions = JePermission.SELECT)
public class XTaskDefine extends XlsBase {

	@JaLang("场景名称")
	private String name;

	@JaLang("关卡")
	private TaskPass[] taskPasses;

	/**
	 * @author absir
	 * 
	 */
	public static class TaskPass {

		@JaLang("名称")
		private String name;

		@JaLang("解锁等级")
		private int level;

		@JaLang("任务列表")
		private TaskDetail[] taskDetails;

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the level
		 */
		public int getLevel() {
			return level;
		}

		/**
		 * @return the taskDetails
		 */
		public TaskDetail[] getTaskDetails() {
			return taskDetails;
		}
	}

	/**
	 * @author absir
	 * 
	 */
	public static class TaskDetail {

		@JaLang("名称")
		private String name;

		@JaLang("对话描述")
		private String[] descs;

		@JaLang("体力消耗")
		private int ep;

		@JaLang("获得经验")
		private int exp;

		@JaLang("最大经验")
		private int maxExp;

		@JaLang("掉落金钱")
		private int money;

		@JaLang("最大金钱")
		private int maxMoney;

		@JaLang("小怪波数")
		private int number;

		@JaLang("宝箱数")
		private int lotCount;

		@JaLang("怪物等级")
		private int level;

		@JaLang("怪物最大等级")
		private int maxLevel;

		@JaLang("任务小怪")
		// @JsonSerialize(contentUsing = JiBaseSerializer.class)
		private XCardDefine[] enemies;

		@JaLang("任务BOSS")
		// @JsonSerialize(contentUsing = JiBaseSerializer.class)
		private XCardDefine[] bosses;

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the descs
		 */
		public String[] getDescs() {
			return descs;
		}

		/**
		 * @return the ep
		 */
		public int getEp() {
			return ep;
		}

		/**
		 * @return the exp
		 */
		public int getExp() {
			return exp;
		}

		/**
		 * @return the maxExp
		 */
		public int getMaxExp() {
			return maxExp;
		}

		/**
		 * @return the money
		 */
		public int getMoney() {
			return money;
		}

		/**
		 * @return the maxMoney
		 */
		public int getMaxMoney() {
			return maxMoney;
		}

		/**
		 * @return the number
		 */
		public int getNumber() {
			return number;
		}

		/**
		 * @return the lotCount
		 */
		public int getLotCount() {
			return lotCount;
		}

		/**
		 * @return the level
		 */
		public int getLevel() {
			return level;
		}

		/**
		 * @return the maxLevel
		 */
		public int getMaxLevel() {
			return maxLevel;
		}

		/**
		 * @return the enemies
		 */
		public XCardDefine[] getEnemies() {
			return enemies;
		}

		/**
		 * @return the bosses
		 */
		public XCardDefine[] getBosses() {
			return bosses;
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the taskPasses
	 */
	public TaskPass[] getTaskPasses() {
		return taskPasses;
	}

}
