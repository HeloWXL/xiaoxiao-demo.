import {get} from '@/util/axios'

export function queryUserFile(params){
    return get('/uploadFile/selectAll',params)
}