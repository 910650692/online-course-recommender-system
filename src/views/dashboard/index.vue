<template>
  <div class="dashboard-container">
    <panel-group />
    <el-table
      v-loading="listLoading"
      :data="paginatedList"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="Course ID" width="95">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column label="Course Name">
        <template slot-scope="scope">
          {{ scope.row.courseName }}
        </template>
      </el-table-column>
      <el-table-column label="University">
        <template slot-scope="scope">
          {{ scope.row.university }}
        </template>
      </el-table-column>
      <el-table-column label="Difficulty Level">
        <template slot-scope="scope">
          {{ scope.row.difficultyLevel }}
        </template>
      </el-table-column>
      <el-table-column label="Course Rating" width="80">
        <template slot-scope="scope">
          {{ scope.row.courseRating }}
        </template>
      </el-table-column>
      <el-table-column label="Course URL">
        <template slot-scope="scope">
          <a :href="scope.row.courseUrl" target="_blank">
            {{ scope.row.courseUrl }}
          </a>
        </template>
      </el-table-column>
      <el-table-column label="Course Description">
        <template slot-scope="scope">
          <div class="scrollable-content">
            {{ scope.row.courseDescription }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="Skills">
        <template slot-scope="scope">
          {{ scope.row.skills }}
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      background
      :current-page="currentPage"
      :page-size="pageSize"
      layout="total, prev, pager, next, jumper"
      :total="total"
      @current-change="handlePageChange"
    />
    <pie-chart />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import PanelGroup from './PanelGroup.vue'
import { getCourseList } from '@/api/table'
import PieChart from '../chart/PieChart.vue'
export default {
  name: 'Dashboard',
  components: { PanelGroup, PieChart },
  data() {
    return {
      list: [], // 用于保存 getList 函数的返回值
      paginatedList: [], // 当前页的数据
      listLoading: false, // 用于表示是否正在加载数据
      currentPage: 1, // 当前页码
      pageSize: 10, // 每页显示的条目数
      total: 0 // 总条目数
    }
  },
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  created() {
    this.fetchData()
    console.log('request sended')
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getCourseList(this.currentPage, this.pageSize).then(response => {
        this.list = response.data
        this.total = response.data.length
        this.paginate()
        this.listLoading = false
      }).catch(error => {
        console.error('Failed to fetch data:', error)
        this.listLoading = false
        this.$message.error('Failed to fetch courses')
      })
    },
    paginate() {
      const start = (this.currentPage - 1) * this.pageSize
      const end = start + this.pageSize
      this.paginatedList = this.list.slice(start, end)
    },
    handlePageChange(newPage) {
      this.currentPage = newPage
      this.fetchData()
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
<style scoped>
.scrollable-content {
  max-height: 100px;
  overflow-y: auto;
}
</style>