<template>
  <div class="app-container">
    <el-button @click="handleAdd" type="primary" style="margin-bottom: 20px;">Add</el-button>
    <el-table :data="paginatedList" style="width: 100%">
      <!-- 其他代码 -->

    </el-table>
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
          <div class="scrollable-content">
            {{ scope.row.skills }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="Operations">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 修改表单 -->
    <el-dialog :visible.sync="dialogVisible" title="Edit Course">
      <el-form :model="form">
        <el-form-item label="Course Name">
          <el-input v-model="form.courseName" />
        </el-form-item>
        <el-form-item label="University">
          <el-input v-model="form.university" />
        </el-form-item>
        <el-form-item label="Difficulty Level">
          <el-input v-model="form.difficultyLevel" />
        </el-form-item>
        <el-form-item label="Course Rating">
          <el-input v-model="form.courseRating" />
        </el-form-item>
        <el-form-item label="Course URL">
          <el-input v-model="form.courseUrl" />
        </el-form-item>
        <el-form-item label="Course Description">
          <el-input v-model="form.courseDescription" />
        </el-form-item>
        <el-form-item label="Skills">
          <el-input v-model="form.skills" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="submitForm">Submit</el-button>
      </span>
    </el-dialog>
    <el-pagination
      background
      :current-page="currentPage"
      :page-size="pageSize"
      layout="total, prev, pager, next, jumper"
      :total="total"
      @current-change="handlePageChange"
    />
  </div>
</template>

<script>
import { getCourseList, editCourse, deleteCourse } from '@/api/table'
export default {
  data() {
    return {
      list: [], // 用于保存 getList 函数的返回值
      paginatedList: [], // 当前页的数据
      listLoading: false, // 用于表示是否正在加载数据
      currentPage: 1, // 当前页码
      pageSize: 10, // 每页显示的条目数
      total: 0, // 总条目数
      dialogVisible: false,
      form: {
        id: '',
        courseName: '',
        university: '',
        difficultyLevel: '',
        courseRating: '',
        courseUrl: '',
        courseDescription: '',
        skills: ''
      },
      currentRow: null
    }
  },
  created() {
    this.fetchData()
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
    },
    handleEdit(index, row) {
      this.currentRow = row
      this.form = { ...row }
      this.dialogVisible = true
    },
    submitForm() {
      console.log(this.form)
      editCourse(this.form)
        .then(response => {
          this.dialogVisible = false
          this.$message({
            message: 'Course updated successfully',
            type: 'success'
          })
          this.fetchData()
        })
        .catch(() => {
          this.$message({
            message: 'Failed to update course',
            type: 'error'
          })
        })
    },
    handleDelete(index, row) {
    // 在这里处理删除操作
    // 例如，你可以发送一个请求来删除数据
    // 然后刷新数据
      this.$confirm('Are you sure you want to delete this course?', 'Warning', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        type: 'warning'
      }).then(() => {
        deleteCourse(row.id)
          .then(() => {
            this.$message({
              type: 'success',
              message: 'Course deleted successfully'
            })
            this.fetchData()
          })
          .catch(() => {
            this.$message({
              type: 'error',
              message: 'Failed to delete course'
            })
          })
      }).catch(() => {})
    },
    handleAdd() {
      this.dialogVisible = true
      this.form = {
        name: '',
        description: ''
        // 其他字段
      }
    }
  }
}
</script>
<style scoped>
.scrollable-content {
  max-height: 100px;
  overflow-y: auto;
}
</style>_index_response_error
