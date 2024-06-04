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
