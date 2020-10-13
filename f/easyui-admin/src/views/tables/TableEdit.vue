<template>
  <div>
    <h2>数据表格--对话框编辑</h2>
    <LinkButton iconCls="icon-add" @click="addRow()" style="width:80px;margin-bottom:4px">添加</LinkButton>
    <DataGrid :data="data">
      <GridColumn field="itemid" title="Item ID"></GridColumn>
      <GridColumn field="name" title="Name"></GridColumn>
      <GridColumn field="listprice" title="List Price" align="right"></GridColumn>
      <GridColumn field="unitcost" title="Unit Cost" align="right"></GridColumn>
      <GridColumn field="attr" title="Attribute" width="30%"></GridColumn>
      <GridColumn field="status" title="Status" align="center">
        <template slot="body" slot-scope="scope">
          {{scope.row.status ? 'T' : ''}}
        </template>
      </GridColumn>
      <GridColumn field="act" title="操作" align="center">
        <template slot="body" slot-scope="scope">
          <ButtonGroup style="height:24px">
            <LinkButton iconCls="icon-edit" @click="editRow(scope.row)">编辑</LinkButton>
            <LinkButton iconCls="icon-remove" @click="deleteRow(scope.row)">删除</LinkButton>
          </ButtonGroup>
        </template>
      </GridColumn>
    </DataGrid>

    <Dialog ref="dlg" bodyCls="f-column" :title="title" :modal="true" closed :dialogStyle="{height:''}">
      <div class="f-full" style="overflow:auto">
        <Form ref="form" :model="model" :rules="rules" @validate="errors=$event" style="padding:20px 50px">
          <div style="margin-bottom:20px">
            <Label for="itemid">Item ID:</Label>
            <TextBox inputId="itemid" name="itemid" v-model="model.itemid"></TextBox>
            <div class="error">{{getError('itemid')}}</div>
          </div>
          <div style="margin-bottom:20px">
            <Label for="name">Name:</Label>
            <TextBox inputId="name" name="name" v-model="model.name"></TextBox>
            <div class="error">{{getError('name')}}</div>
          </div>
          <div style="margin-bottom:20px">
            <Label for="listprice">List Price:</Label>
            <NumberBox inputId="listprice" name="listprice" :precision="1" v-model="model.listprice"></NumberBox>
          </div>
          <div style="margin-bottom:20px">
            <Label for="unitcost">Unit Cost:</Label>
            <NumberBox inputId="unitcost" name="unitcost" v-model="model.unitcost"></NumberBox>
          </div>
          <div style="margin-bottom:20px">
            <Label for="attr">Attribute:</Label>
            <TextBox inputId="attr" name="attr" v-model="model.attr"></TextBox>
          </div>
          <div style="margin-bottom:20px">
            <Label for="status">Status:</Label>
            <CheckBox inputId="status" name="status" v-model="model.status"></CheckBox>
          </div>
        </Form>
      </div>
      <div class="buttons f-noshrink">
        <LinkButton @click="saveRow()">Save</LinkButton>
        <LinkButton @click="$refs.dlg.close()">Cancel</LinkButton>
      </div>
    </Dialog>

  </div>
</template>
 
<script>
export default {
  data() {
    return {
      data: [],
      title: '',
      model: {
        itemid: null,
        name: null,
        listprice: null,
        unitcost: null,
        attr: null,
        status: true
      },
      rules: {
        itemid: 'required',
        name: 'required'
      },
      errors: {},
      editingRow: null
    }
  },
  created() {
    this.data = [
      { "code": "FI-SW-01", "name": "Koi", "unitcost": 10.00, "status": true, "listprice": 36.50, "attr": "Large", "itemid": "EST-1" },
      { "code": "K9-DL-01", "name": "Dalmation", "unitcost": 12.00, "status": true, "listprice": 18.50, "attr": "Spotted Adult Female", "itemid": "EST-10" },
      { "code": "RP-SN-01", "name": "Rattlesnake", "unitcost": 12.00, "status": false, "listprice": 38.50, "attr": "Venomless", "itemid": "EST-11" },
      { "code": "RP-SN-01", "name": "Rattlesnake", "unitcost": 12.00, "status": false, "listprice": 26.50, "attr": "Rattleless", "itemid": "EST-12" },
      { "code": "RP-LI-02", "name": "Iguana", "unitcost": 12.00, "status": true, "listprice": 35.50, "attr": "Green Adult", "itemid": "EST-13" },
      { "code": "FL-DSH-01", "name": "Manx", "unitcost": 12.00, "status": false, "listprice": 158.50, "attr": "Tailless", "itemid": "EST-14" },
      { "code": "FL-DSH-01", "name": "Manx", "unitcost": 12.00, "status": false, "listprice": 83.50, "attr": "With tail", "itemid": "EST-15" },
      { "code": "FL-DLH-02", "name": "Persian", "unitcost": 12.00, "status": true, "listprice": 23.50, "attr": "Adult Female", "itemid": "EST-16" },
      { "code": "FL-DLH-02", "name": "Persian", "unitcost": 12.00, "status": true, "listprice": 89.50, "attr": "Adult Male", "itemid": "EST-17" },
      { "code": "AV-CB-01", "name": "Amazon Parrot", "unitcost": 92.00, "status": true, "listprice": 63.50, "attr": "Adult Male", "itemid": "EST-18" }
    ];
  },
  methods: {
    addRow() {
      this.model = {
        itemid: null,
        name: null,
        listprice: null,
        unitcost: null,
        attr: null,
        status: true
      };
      this.title = 'Add';
      this.$refs.dlg.open();
    },
    editRow(row) {
      this.editingRow = row;
      this.model = Object.assign({}, row);
      this.title = 'Edit';
      this.$refs.dlg.open();
    },
    saveRow() {
      this.$refs.form.validate(errors => {
        if (!errors) {
          const newRow = Object.assign({}, this.model);
          if (this.editingRow) {
            const index = this.data.indexOf(this.editingRow);
            this.data.splice(index, 1, newRow);
            this.editingRow = null;
          } else {
            this.data.unshift(newRow)
          }
          this.$refs.dlg.close();
        }
      })
    },
    deleteRow(row) {
      this.$messager.confirm({
        title: 'Confirm',
        msg: 'Are you sure you want to delete the row?',
        result: (r) => {
          if (r) {
            const index = this.data.indexOf(row);
            this.data.splice(index, 1);
          }
        }
      })
    },
    getError(name) {
      return this.errors[name] && this.errors[name].length
        ? this.errors[name][0]
        : null;
    },
    hasError(name) {
      return this.getError(name) != null;
    }
  }
}
</script>
<style>
.buttons {
  text-align: right;
  padding: 10px;
}
.buttons .l-btn {
  width: 80px;
  margin-left: 5px;
}
.error {
  color: red;
  font-size: 12px;
  margin: 4px 0;
  margin-left: 80px;
}
</style>