<template>
  <div class="app-container" id="loadingBody">
    <div class="filter-container" style="margin: 10px 0 10px 0;">
      <el-input clearable class="filter-item" style="width: 200px;" v-model="keyword" placeholder="请输入指标名"></el-input>
      <el-select v-model="searchValue" clearable placeholder="请选择类别">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
	    <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFind">查找</el-button>
      <el-button class="filter-item" type="primary" @click="handleAdd" icon="el-icon-edit">添加</el-button>      
    </div>
    <el-table :data="tableData"  style="width: 100%">
      <el-table-column type="selection"></el-table-column>
      <el-table-column label="序号" width="60">
	      <template slot-scope="scope">
	        <span >{{scope.$index + 1}}</span>
	      </template>
	    </el-table-column>

	    <el-table-column label="指标名称" width="250">
	      <template slot-scope="scope">
	        <span>{{ scope.row.name }}</span>
	      </template>
	    </el-table-column>
	    
      <el-table-column label="类型" width="100">
	   	  <template slot-scope="scope">
          <el-tag type="warning" v-if="scope.row.typeStatus == 0 ">指标</el-tag>          
          <el-tag type="warning" v-if="scope.row.typeStatus == 1 ">观测点</el-tag>          
	   	  </template>
	    </el-table-column>

	    <el-table-column label="创建时间" width="180">
	      <template slot-scope="scope">
	        <span >{{ scope.row.createTime }}</span>
	      </template>
	    </el-table-column>
	    
	   	<el-table-column label="状态" width="100">
	   	  <template slot-scope="scope">
		   	  <template v-if="scope.row.status == 0">
		        <span>正常</span>
		      </template>
		      <template v-if="scope.row.status == 1">
		        <span>已删除</span>
		      </template>
	   	  </template>
	    </el-table-column>
	    
	    <el-table-column label="操作" fixed="right" min-width="150"> 
	      <template slot-scope="scope" >
	      	<el-button @click="handleEdit(scope.row)" type="primary" size="small">编辑</el-button>
	        <el-button @click="handleDelete(scope.row)" type="danger" size="small">删除</el-button>
	      </template>
	    </el-table-column>     	    
	  </el-table>

    <!--分页-->
    <div class="block">
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-size="pageSize"
          layout="total, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
    </div>

    <!-- 添加或修改对话框 -->
		<el-dialog :title="title" :visible.sync="dialogFormVisible">
		  <el-form :model="form">
		  	
        <el-form-item label="指标名称" :label-width="formLabelWidth">
		      <el-input v-model="form.name" auto-complete="off"></el-input>
		    </el-form-item>
		    
		    <el-form-item label="类型" :label-width="formLabelWidth">		      
          <el-select v-model="form.typeStatus" placeholder="请选择类别">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
		    </el-form-item>

		  </el-form>
		  <div slot="footer" class="dialog-footer">
		    <el-button @click="dialogFormVisible = false">取 消</el-button>
		    <el-button type="primary" @click="submitForm">确 定</el-button>
		  </div>
		</el-dialog>

  </div>
</template>

<script>
import {
  getIndicatorList,
  addIndicator,
  editIndicator,
  deleteIndicator
} from "@/api/indicator";
import { formatData } from "@/utils/webUtils";
import { Loading } from 'element-ui';
export default {
  data() {
    return {
      loadingInstance: null,
      loadingOption: {
        target: "#loadingBody",
        body: true,
        text: "正在加载中~"
      },
      tableData: [],
      keyword: "",
      currentPage: 1,
      pageSize: 10,
      total: 0, //总数量
      title: "添加指标",
      dialogFormVisible: false, //控制弹出框
      formLabelWidth: "120px",
      isEditForm: false,
      form: {
        uid: null,
        name: ""
      },
      searchValue: "",
      options: [
        {
          value: 0,
          label: "指标"
        },
        {
          value: 1,
          label: "观测点"
        }
      ]
    };
  },
  created() {
    this.loadingInstance = Loading.service(this.loadingOption);
    this.indicatorList();
  },
  methods: {
    getFormObject: function() {
      var formObject = {
        uid: null,
        name: "",
        typeStatus: 0 // 0：指标
      };
      return formObject;
    },
    indicatorList: function() {
      var params = new URLSearchParams();
      params.append("typeStatus", this.searchValue);
      params.append("keyword", this.keyword);        
      params.append("page", this.currentPage -1 );
      params.append("size", this.pageSize);
      getIndicatorList(params).then(response => {
        if (response.code == "success") {
          console.log(response);
          this.tableData = response.data.content;
          this.total = response.data.total;
          this.pageSize = response.data.pageable.size;
          this.currentPage = response.data.pageable.page + 1;
        }
        this.loadingInstance.close();
      });
    },
    handleCurrentChange: function(val) {
      console.log("页码改变", val);
      this.currentPage = val ;
      this.indicatorList();
    },
    handleFind: function() {
      console.log("点击了查找");
      this.indicatorList();
    },
    handleAdd: function() {
      console.log("点击了添加");
      this.dialogFormVisible = true;
      this.form = this.getFormObject();
      this.isEditForm = false;
    },
    handleEdit: function(row) {
      console.log("点击了编辑");
      this.title = "编辑指标";
      this.dialogFormVisible = true;
      this.form = row;
      this.isEditForm = true;
    },
    handleDelete: function(row) {
      var params = new URLSearchParams();
      params.append("uid", row.uid);
      console.log("点击了删除", row);
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          deleteIndicator(params).then(response => {
            if(response.code == "success") {
              this.$message({
                type: "success",
                message: response.data
              });
              this.indicatorList();
            }            
          })

        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    submitForm: function() {
      console.log("点击了提交");
      var params = formatData(this.form);
      if (this.isEditForm) {
        editIndicator(params).then(response => {
          console.log(response);
          if (response.code == "success") {
            this.$message({
              type: "success",
              message: response.data
            });
          } else {
            this.$message({
              type: "error",
              message: response.data
            });
          }
          this.dialogFormVisible = false;
          this.indicatorList();
        });
      } else {
        addIndicator(params).then(response => {
          console.log(response);
          this.$message({
            type: "success",
            message: response.data
          });
          this.dialogFormVisible = false;
          this.indicatorList();
        });
      }
    }
  }
};
</script>
