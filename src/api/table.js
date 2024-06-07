import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/vue-admin-template/table/list',
    method: 'get',
    params
  })
}

// 拿后端课程数据
export function getCourseList(params) {
  return request({
    url: '/course/list',
    method: 'get',
    params
  })
}

export function getCourseDifficultyCounts() {
  return request({
    url: '/course/countByDifficultyLevel',
    method: 'get'
  })
}
export function editCourse(data) {
  return request({
    url: '/course/update',
    method: 'post',
    data
  })
}
export function deleteCourse(id) {
  return request({
    url: '/course/delete',
    method: 'post',
    params: { id }
  })
}
export function addCourse(data) {
  return request({
    url: '/course/add',
    method: 'post',
    data
  })
}
export function getTotalCourses() {
  return request({
    url: '/course/total',
    method: 'get'
  })
}
export function getTotalUsers() {
  return request({
    url: '/user/total',
    method: 'get'
  })
}
export function courseRating(userId, courseId, rating) {
  return request({
    url: '/course/rating',
    method: 'post',
    params: { userId, courseId, rating }
  })
}
