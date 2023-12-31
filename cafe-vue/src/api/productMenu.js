import request from '@/utils/request'

//Get productMenu list
export function productMenuList(data, category) {
  return request({
    url: '/menu/' + category,
    method: 'post',
    data
  })
}

export function getProductDetail(data, productName) {
  return request({
    url: '/menu/product/' + productName,
    method: 'post',
    data
  })
}
