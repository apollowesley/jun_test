let menus = [{
		text: "Forms",
		iconCls: "fa fa-wpforms",
		state: "open",
		children: [{
				text: "简单表单",
				path: "hello"
			},
			{
				text: "测试一下",
				path: "test"
			},
			{
				text: "数据表格",
				path: "table"
			},
			{
				text: "表格编辑",
				path: "editTable"
			},
			{
				text: "其他",
				path: "other"
			},
			{
				text: "单页面无侧边栏",
				path: "singledemo"
			}
		]
	},
	{
		text: "Mail",
		iconCls: "fa fa-at",
		selected: true,
		children: [{
				text: "Inbox"
			},
			{
				text: "Sent"
			},
			{
				text: "Trash",
				children: [{
						text: "Item1"
					},
					{
						text: "Item2"
					}
				]
			}
		]
	},
	{
		text: "Layout",
		iconCls: "fa fa-table",
		children: [{
				text: "Panel"
			},
			{
				text: "Accordion"
			},
			{
				text: "Tabs"
			}
		]
	}
]

export default menus;

