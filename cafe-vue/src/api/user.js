import request from '@/utils/request'

export function userLogin(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

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

//Edit user
export function userUpdate(data) {
  return request({
    url: '/user/update',
    method: 'post',
    data
  })
}

//Delete user
export function userDelete(id) {
  return request({
    url: '/user/delete',
    method: 'post',
    params: {
      id
    }
  })
}

//Batch delete user
export function userBatchDelete(data) {
  return request({
    url: '/user/delete/batch',
    method: 'post',
    data
  })
}
