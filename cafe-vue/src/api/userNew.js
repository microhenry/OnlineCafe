import request from '@/utils/requestNew'

//Get user list
export function userList(data) {
  return request({
    url: '/user/list',
    method: 'post',
    data
  })
}

//Add user
export function userAdd(data) {
  return request({
    url: '/user/add',
    method: 'post',
    data
  })
}
