<template>
  <div class="app-container">
    <h1 class="dashboard-text">Welcome, {{ name }}</h1>
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
          {{ formatRating(scope.row.courseRating) }}
        </template>
      </el-table-column>
      <el-table-column label="Course URL">
        <template slot-scope="scope">
          <a :href="scope.row.courseUrl" target="_blank">
            {{ scope.row.courseUrl }}
          </a>
        </template>
      </el-table-column>
      <!-- <el-table-column label="Course Description">
        <template slot-scope="scope">
          <div class="scrollable-content">
            {{ scope.row.courseDescription }}
          </div>
        </template>
      </el-table-column> -->
      <el-table-column label="Skills">
        <template slot-scope="scope">
          <div class="scrollable-content">
            {{ scope.row.skills }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="Operations">
        <template slot-scope="scope">
          <el-button v-if="role === 'admin'" size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
          <el-button v-if="role === 'admin'" size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">Delete</el-button>
          <el-button v-if="role === 'user' && collectedCourseIds.has(scope.row.id)" size="mini" type="danger" @click="handleUnCollect(scope.$index, scope.row)">UnCollect</el-button>
          <el-button v-else-if="role === 'user' && !collectedCourseIds.has(scope.row.id)" size="mini" type="primary" @click="handleCollect(scope.$index, scope.row)">Collect</el-button>
          <el-button v-if="role === 'user'" size="mini" type="info" @click="openRatingDialog(scope.$index, scope.row)">Rating</el-button>
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
    <!-- 评分对话 -->
    <el-dialog :visible.sync="ratingDialogVisible" title="Rate Course">
      <el-rate
        v-model="rating"
        :colors="colors"
      />
      <span slot="footer" class="dialog-footer">
        <el-button @click="ratingDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="submitRating">Submit</el-button>
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
import { getCourseList, editCourse, deleteCourse, addCourse, courseRating } from '@/api/table'
import { collect, cancelCollect, getCollectList } from '@/api/user'
import { mapGetters } from 'vuex'
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
      currentRow: null,
      ratingDialogVisible: false,
      rating: null,
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
      courses: [], // 用于保存已收藏的课程
      collectedCourseIds: new Set() // 用于保存已收藏的课程的 ID
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'role'
    ])
  },
  created() {
    this.fetchData()
    this.fetchCollectList()
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
    async fetchCollectList() {
      try {
        const userId = this.$store.getters.userId // 假设 userId 存储在 Vuex store 的 getters 中
        const { data } = await getCollectList(userId)
        console.log('Collected courses:', data)
        // 检查 data 是否为非空数组
        if (Array.isArray(data)) {
          this.collectedCourseIds = new Set(data.map(course => course.id));
        } else {
          this.collectedCourseIds = new Set(); // 确保是空的 Set，避免错误
        }
        this.collectedCourseIds = new Set(data.map(course => course.id))
        this.courses.forEach(course => {
          course.isCollected = this.collectedCourseIds.has(course.id)
        })
      } catch (error) {
        console.error('Failed to fetch collect list', error)
      }
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
        id: '',
        courseName: '',
        university: '',
        difficultyLevel: '',
        courseRating: '',
        courseUrl: '',
        courseDescription: '',
        skills: ''
      }
      addCourse(this.form)
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
    openRatingDialog(index, row) {
      this.currentRow = row
      this.ratingDialogVisible = true
    },
    submitRating() {
      const userId = this.$store.getters.userId
      courseRating(userId, this.currentRow.id, this.rating)
        .then(() => {
          this.$message({
            message: 'Course rated successfully',
            type: 'success'
          })
          this.fetchData()
          this.ratingDialogVisible = false
        })
        .catch(() => {
          this.$message({
            message: 'Failed to rate course',
            type: 'error'
          })
        })
    },
    formatRating(rating) {
      return parseFloat(rating).toFixed(1)
    },
    async handleCollect(index, row) {
      try {
        await collect(this.$store.getters.userId, row.id)
        this.$message({
          message: 'Course collected successfully',
          type: 'success'
        })
      } catch (error) {
        this.$message({
          message: 'Failed to collect course',
          type: 'error'
        })
      }
      await this.fetchCollectList()
    },
    async handleUnCollect(index, row) {
      try {
        await cancelCollect(this.$store.getters.userId, row.id)
        this.$message({
          message: 'Course uncollected successfully',
          type: 'success'
        })
      } catch (error) {
        this.$message({
          message: 'Failed to uncollect course',
          type: 'error'
        })
      }
      await this.fetchCollectList()
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
