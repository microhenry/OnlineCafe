import request from '@/utils/request'

//Get product list
export function productList(data) {
  return request({
    url: '/user/product/list',
    method: 'post',
    data
  })
}

//Get product detail
export function getProductDetail(data, productName) {
  return request({
    url: '/user/product/detail/' + productName,
    method: 'post',
    data
  })
}

// Add product
export function addProduct(data) {
  return request({
    url: '/user/product/add',
    method: 'post',
    data
  })
}

// Update product
export function updateProduct(data) {
  return request({
    url: '/user/product/update',
    method: 'post',
    data
  })
}

// Delete product
export function deleteProduct(id) {
  return request({
    url: '/user/product/delete',
    method: 'post',
    id
  })
}

// Batch delete product
export function batchDeleteProduct(data) {
  return request({
    url: '/user/product/delete/batch',
    method: 'post',
    data
  })
}
