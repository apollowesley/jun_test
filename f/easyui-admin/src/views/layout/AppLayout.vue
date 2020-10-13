<template>
	<div>
		<div class="f-column">
			<div class="main-header f-row">
				<div class="f-row f-full">
					<div class="main-title f-animate f-row" :style="{width:width+'px'}">
						<img class="app-logo" src="https://www.jeasyui.com/favicon.ico">
						<span v-if="!collapsed">{{title}}</span>
					</div>
					<div class="main-bar f-full">
						<span class="main-toggle fa fa-bars" @click="toggle()"></span>
					</div>
				</div>
			</div>
			<div class="f-row f-full">
				<div class="sidebar-body f-animate" :style="{width:width+'px'}">
					<div v-if="!collapsed" class="sidebar-user" >
						Welcome Admin
					</div>
					<SideMenu :data="menus" :border="false" :style="{width:width+'px'}" :collapsed="collapsed" @itemClick="onItemClick($event)"></SideMenu>	<!-- 点击事件 onItemClick($event) -->
				</div>
				<div class="main-body">
					<router-view></router-view>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import menus from '@/menus'
	export default {
		data() {
			return {
				title: 'EasyUI Admin',
				width: 200,
				collapsed: false,
				selectedMenu: null,
				menus: menus
			};
		},
		methods: {
			toggle() {
				this.collapsed = !this.collapsed;
				this.width = this.collapsed ? 50 : 200;
			},
			onItemClick(item) {
				this.selectedMenu = item;
				this.$router.push(this.selectedMenu.path);	//菜单跳转
			}
		}
	};
</script>path
<style>
	.sidemenu .accordion .panel-title {
		color: #b8c7ce;
	}

	.sidemenu .accordion .accordion-header {
		background: #222d32;
		color: #b8c7ce;
	}

	.sidemenu .accordion .accordion-body {
		background: #2c3b41;
		color: #8aa4af;
	}

	.sidemenu .accordion .accordion-header-selected {
		background: #1e282c;
	}

	.sidemenu .tree-node-hover {
		background: #2c3b41;
		color: #fff;
	}

	.sidemenu .tree-node-selected {
		background: #2c3b41;
		color: #fff;
	}

	.sidemenu .accordion-header .panel-tool {
		display: none;
	}

	.sidemenu .accordion-header::after,
	.sidemenu .tree-node-nonleaf::after {
		display: inline-block;
		vertical-align: top;
		border-style: solid;
		transform: rotate(45deg);
		width: 4px;
		height: 4px;
		content: "";
		position: absolute;
		right: 10px;
		top: 50%;
		margin-top: -3px;
		border-width: 0 1px 1px 0;
	}

	.sidemenu .accordion-header-selected::after {
		transform: rotate(-135deg);
	}

	.sidemenu .tree-node-nonleaf::after {
		transform: rotate(-135deg);
	}

	.sidemenu .tree-node-nonleaf-collapsed::after {
		transform: rotate(45deg);
	}

	.sidemenu-collapsed .accordion-header::after {
		display: none;
	}

	.sidemenu-tooltip .accordion {
		border-color: #1e282c;
	}

	html,
	body {
		margin: 0;
		padding: 0;
	}

	.app-logo {
		width: 24px;
		height: 24px;
		color: #fff;
		margin: 13px 10px;
	}

	.main-header {
		background: #3c8dbc;
		color: #fff;
		line-height: 50px;
		height: 50px;
	}

	.main-title {
		background: #367fa9;
		font-size: 20px;
		text-align: center;
		overflow: hidden;
	}

	.main-bar {
		background: #3c8dbc;
	}

	.main-toggle {
		position: relative;
		display: inline-block;
		width: 16px;
		height: 16px;
		cursor: pointer;
		color: #fff;
		margin: 0 10px;
	}

	.main-body {
		background: #ecf0f5;
		min-height: -webkit-fill-available;
		width: 80%;
		padding: 1%;
		flex: 1 1 auto;
	}

	.sidebar-body {
		background: #222d32;
	}

	.sidebar-user {
		color: #fff;
		padding: 20px;
		line-height: 20px;
	}
</style>
