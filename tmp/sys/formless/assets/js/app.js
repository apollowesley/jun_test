import '@/css/app'

import 'phoenix_html'
import 'bootstrap'

import Sortable from 'sortablejs'


$('#columns_reorder .list-group').each(function (idx, ele) {
  Sortable.create(ele)
})
