<template>
	<div id="context">
		<img alt="Vue logo" src="@/assets/logo.png">
		<h2>Basic LinkButton</h2>
		<div style="margin-bottom:20px">
			<LinkButton iconCls="icon-add">Add</LinkButton>
			<LinkButton iconCls="icon-remove">Remove</LinkButton>
			<LinkButton iconCls="icon-save">Save</LinkButton>
			<LinkButton iconCls="icon-cut" :disabled="true">Cut</LinkButton>
		</div>

		<div>
			<LinkButton iconCls="icon-search" style="width:80px">Search</LinkButton>
			<LinkButton iconCls="icon-print" style="width:80px">Print</LinkButton>
			<LinkButton iconCls="icon-reload" style="width:80px">Reload</LinkButton>
			<LinkButton iconCls="icon-help" style="width:80px">Help</LinkButton>
		</div>

		<h2>Plain Button</h2>
		<Panel :bodyStyle="{padding:'5px'}">
			<LinkButton iconCls="icon-add" :plain="true">Add</LinkButton>
			<LinkButton iconCls="icon-reload" :plain="true">Reload</LinkButton>
			<LinkButton iconCls="icon-search" :plain="true">Search</LinkButton>
			<LinkButton iconCls="icon-cut" :plain="true">Cut</LinkButton>
			<LinkButton iconCls="icon-print" :plain="true">Print</LinkButton>
			<LinkButton iconCls="icon-help" :plain="true"></LinkButton>
			<LinkButton iconCls="icon-save" :plain="true"></LinkButton>
			<LinkButton iconCls="icon-back" :plain="true"></LinkButton>
		</Panel>

		<h2>Basic MenuButton</h2>
		<Panel :bodyStyle="{padding:'5px'}">
			<LinkButton :plain="true" :click="test()">Home</LinkButton>
			<MenuButton text="Edit" :plain="true" iconCls="icon-edit">
				<Menu>
					<MenuItem text="Undo" iconCls="icon-undo">
					</MenuItem>
					<MenuItem text="Redo" iconCls="icon-redo">
					</MenuItem>
					<MenuSep></MenuSep>
					<MenuItem text="Cut">
					</MenuItem>
					<MenuItem text="Copy">
					</MenuItem>
					<MenuItem text="Paste">
					</MenuItem>
					<MenuSep></MenuSep>
					<MenuItem text="Toolbar">
					<SubMenu>
						<MenuItem text="Address">
						</MenuItem>
						<MenuItem text="Link">
						</MenuItem>
						<MenuItem text="Navigation Toolbar">
						</MenuItem>
						<MenuItem text="Bookmark Toolbar">
						</MenuItem>
						<MenuSep></MenuSep>
						<MenuItem text="New Toolbar...">
						</MenuItem>
					</SubMenu>
					</MenuItem>
					<MenuItem text="Delete" iconCls="icon-remove">
					</MenuItem>
					<MenuItem text="Select All">
					</MenuItem>
				</Menu>
			</MenuButton>
			<MenuButton text="Help" :plain="true" iconCls="icon-help">
				<Menu>
					<MenuItem text="Help">
					</MenuItem>
					<MenuItem text="Update">
					</MenuItem>
					<MenuItem text="About">
					</MenuItem>
				</Menu>
			</MenuButton>
			<MenuButton text="About" :plain="true">
				<Menu :noline="true">
					<div style="padding:10px">
						<img src="https://www.jeasyui.com/images/logo1.png" style="width:150px;height:50px">
					</div>
				</Menu>
			</MenuButton>
		</Panel>

		<router-link to="/hello">hello</router-link>&nbsp;|&nbsp;
		<router-link to="/test">test</router-link>
		<router-view></router-view>

	</div>
</template>

<script>
	
	export default {
		name: 'context',
		methods: {
			test() {
				
			}
		}
	}
</script>

<style scoped>
#context {
 font-family: 'Avenir', Helvetica, Arial, sans-serif;
-webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
