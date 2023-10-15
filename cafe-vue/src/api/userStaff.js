import request from '@/utils/request'

export function staffLogin(data) {
  return request({
    url: '/loginStaff',
    method: 'post',
    data
  })
}
