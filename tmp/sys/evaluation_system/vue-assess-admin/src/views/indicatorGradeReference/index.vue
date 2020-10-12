<template>
  <div class="app-container" id="loadingBody">
    <div class="filter-container" style="margin: 10px 0 10px 0;">
      <el-input clearable class="filter-item" style="width: 200px;" v-model="keyword" placeholder="请输入指标名"></el-input>
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

	    <el-table-column label="参考名称" width="250">
	      <template slot-scope="scope">
	        <span>{{ scope.row.name }}</span>
	      </template>
	    </el-table-column>
	          
      <el-table-column label="描述" width="250">
	      <template slot-scope="scope">
	        <span>{{ scope.row.description }}</span>
	      </template>
	    </el-table-column>

      <el-table-column label="指标名称" width="250">
	      <template slot-scope="scope">
	        <span v-if="scope.row.indicator">{{ scope.row.indicator.name }}</span>
	      </template>
	    </el-table-column>

      <el-table-column label="分数" width="100">
	      <template slot-scope="scope">
	        <span>{{ scope.row.grade }}</span>
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
		  	
        <el-form-item label="参考名称" :label-width="formLabelWidth">
		      <el-input v-model="form.name" auto-complete="off"></el-input>
		    </el-form-item>

        <el-form-item label="描述" :label-width="formLabelWidth">
		      <el-input v-model="form.description" auto-complete="off"></el-input>
		    </el-form-item>
		            
        <el-form-item label="分数" :label-width="formLabelWidth">
		      <el-input v-model="form.grade" auto-complete="off"></el-input>
		    </el-form-item>

        <el-form-item label="指标" :label-width="formLabelWidth">
		        <el-select
              v-model="form.indicatorUid"
              clearable
              size="small"     
              filterable
              remote              
              reserve-keyword
              placeholder="请输入指标名称"
              :remote-method="remoteMethod"
              :loading="loading">
              <el-option
                v-for="item in indicatorOptions"
                :key="item.uid"
                :label="item.name"
                :value="item.uid">
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
  getIndicatorGradeReferenceList,
  addIndicatorGradeReference,
  editIndicatorGradeReference,
  deleteIndicatorGradeReference
} from "@/api/indicatorGradeReference";

import { getIndicatorList } from "@/api/indicator";

import { formatData } from "@/utils/webUtils";
import { Loading } from "element-ui";
export default {
  data() {
    return {
      loadingInstance: null,
      loadingOption: {
        target: "#loadingBody",
        body: true,
        text: "正在加载中~"
      },
      loading: false, //选择器查询加载loading
      indicatorOptions: [],
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
        name: "",
        description: null,
        indicatorUid: null,
        grade: 0
      },
      searchValue: null
    };
  },
  created() {
    this.loadingInstance = Loading.service(this.loadingOption);
    this.indicatorGradeReferenceList();
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
    indicatorGradeReferenceList: function() {
      var params = new URLSearchParams();
      params.append("page", this.currentPage - 1);
      params.append("size", this.pageSize);
      getIndicatorGradeReferenceList(params).then(response => {
        if (response.code == "success") {
          this.tableData = response.data.indicatorGradeReference.content;
          var tempData = response.data.indicatorGradeReference.content;
          var indicatorData = response.data.inidcator;
          //给每个评分参考增加指标
          if (tempData != null) {
            for (let a = 0; a < tempData.length; a++) {
              for (let b = 0; b < indicatorData.length; b++) {
                console.log(tempData[a].indicatorUid, indicatorData[b].uid);
                if (tempData[a].indicatorUid == indicatorData[b].uid) {
                  tempData[a].indicator = indicatorData[b];
                }
              }
            }
          }
          this.indicatorOptions = indicatorData; //给选项列表设置初始值
          this.tableData = tempData;
          console.log(tempData);
          this.total = response.data.indicatorGradeReference.total;
          this.pageSize = response.data.indicatorGradeReference.pageable.size;
          this.currentPage =
            response.data.indicatorGradeReference.pageable.page + 1;
        }
        this.loadingInstance.close();
      });
    },
    //远程搜索方法
    remoteMethod(query) {
      if (query !== "") {
        console.log("开始了", query);
        this.loading = true;
        console.log("开始查找");
        var params = new URLSearchParams();
        params.append("keyword", query);
        getIndicatorList(params).then(response => {
          if (response.code == "success") {
            console.log(response);
            this.indicatorOptions = response.data.content;
          }
          this.loading = false;
        });
      } else {
        this.indicatorOptions = [];
      }
    },
    handleCurrentChange: function(val) {
      console.log("页码改变", val);
      this.currentPage = val;
      this.indicatorGradeReferenceList();
    },
    handleFind: function() {
      console.log("点击了查找");
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
      console.log("点击了删除", row);
      var params = new URLSearchParams();
      params.append("uid", row.uid);
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          deleteIndicatorGradeReference(params).then(response => {
            if (response.code == "success") {
              this.$message({
                type: "success",
                message: response.data
              });
              this.indicatorGradeReferenceList();
            }
          });
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
        editIndicatorGradeReference(params).then(response => {
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
          this.indicatorGradeReferenceList();
        });
      } else {
        addIndicatorGradeReference(params).then(response => {
          console.log(response);
          this.$message({
            type: "success",
            message: response.data
          });
          this.dialogFormVisible = false;
          this.indicatorGradeReferenceList();
        });
      }
    }
  }
};
</script>
