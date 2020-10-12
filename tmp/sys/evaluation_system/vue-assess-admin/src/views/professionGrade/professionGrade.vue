<template>
  <div class="app-container" id="loadingBody">
    <div class="filter-container" style="margin: 10px 0 10px 0;">
    <el-select
      v-model="keyword"
      clearable
      size="small"     
      filterable
      remote              
      reserve-keyword
      placeholder="请输入专业名称"
      :remote-method="remoteMethod"
      :loading="loading">
      <el-option
        v-for="item in professionOptions"
        :key="item.uid"
        :label="item.name"
        :value="item.uid">
      </el-option>
    </el-select>      
	    <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFind">查找</el-button>   
    </div>
    <el-table :data="tableData"  style="width: 100%">
      <el-table-column type="selection"></el-table-column>
      <el-table-column label="序号" width="60">
	      <template slot-scope="scope">
	        <span >{{scope.$index + 1}}</span>
	      </template>
	    </el-table-column>

	    <el-table-column label="专业名称" width="150">
	      <template slot-scope="scope">
	        <span>{{ scope.row.profession.name }}</span>
	      </template>
	    </el-table-column>

      <el-table-column label="分数" width="150">
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

  </div>
</template>

<script>
import { getProfessionGradeList } from "@/api/professionGrade";
import { getProfessionList } from "@/api/profession";
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
      tableData: [],
      professionOptions: [],
      loading: false,
      keyword: "",
      currentPage: 1,
      pageSize: 10,
      total: 0, //总数量
      dialogFormVisible: false, //控制弹出框
      formLabelWidth: "120px"
    };
  },
  created() {
    this.loadingInstance = Loading.service(this.loadingOption);
    this.professionGradeList();
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
    professionGradeList: function() {
      var params = new URLSearchParams();
      params.append("professionUid", this.keyword);
      params.append("page", this.currentPage - 1);
      params.append("size", this.pageSize);
      getProfessionGradeList(params).then(response => {
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
        //远程搜索方法
    remoteMethod(query) {
      if (query !== "") {
        this.loading = true;
        var params = new URLSearchParams();
        params.append("keyword", query);
        getProfessionList(params).then(response => {
          if (response.code == "success") {
            console.log(response);
            this.professionOptions = response.data.content;
          }
          this.loading = false;
        });
      } else {
        this.professionOptions = [];
      }
    },
    handleCurrentChange: function(val) {
      console.log("页码改变", val);
      this.currentPage = val;
      this.professionGradeList();
    },
    handleFind: function() {
      console.log("点击了查找");      
      this.professionGradeList();
    }
  }
};
</script>
