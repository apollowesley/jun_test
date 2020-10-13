<template>
  <div class="app-quickview">
    <div class="app-quickview-wrapper">
      <div class="relative bg-light pt-2">
        <a class="absolute t-0 r-0 p-2" style="z-index: 3" @click="close"><i class="fa fa-times"></i></a>
        <ul class="nav nav-tabs nav-line nav-justified nav-justified-xs nav-icon">
          <li class="nav-item">
            <a data-toggle="tab" href="#f1" class="active"><i class="fa fa-globe fa-2x"></i></a>
          </li>
          <li class="nav-item">
            <a data-toggle="tab" href="#f2"><i class="fa fa-exchange fa-2x"></i></a>
          </li>
          <li class="nav-item">
            <a data-toggle="tab" href="#f3"><i class="fa fa-user fa-2x"></i></a>
          </li>
        </ul>
      </div>
      <div class="tab-content">
        <div id="f1" role="tabpanel" class="tab-pane p-3 active">
          <div class="info-line pb-3">
            <div class="item" v-for="item in 6">
              <div class="text-muted">5 minutes ago</div>
              <div>Revert "Use jsDelivr CDN until BootstrapCDN gets updated</div>
            </div>
          </div>
          <div class="btn btn-outline-secondary btn-block">Load more</div>
        </div>
        <div id="f2" role="tabpanel" class="tab-pane">
          <ul class="list-group list-group-gap list-group-sm p-3">
            <li class="list-group-item d-block" :class="'b-' + colors[item % 5]" v-for="item in 6">
              <div class="text-muted">Invoice #43344334</div>
              <div :class="'text-' + colors[item % 5]">Paid</div>
            </li>
          </ul>
        </div>
        <div id="f3" role="tabpanel" class="tab-pane">
          <a class="d-flex pt-2 px-3" v-for="item in 8">
            <span class="mr-3 img rounded-circle bg-pink text-white font-weight-bold">T</span>
            <div class="grow bb-eee pb-2">
              <div>Jonh sall</div>
              <div class="text-muted">designer</div>
            </div>
          </a>
        </div>
      </div>
      <div class="bt-l bg-light p-2">
        <span class="text-muted">Manage: </span>
        <a class="btn bg-facebook text-white rounded-circle btn-icon"><i class="fa fa-facebook"></i></a>
        <a class="btn bg-linkedin text-white rounded-circle btn-icon"><i class="fa fa-linkedin"></i></a>
        <a class="btn bg-twitter text-white rounded-circle btn-icon"><i class="fa fa-twitter"></i></a>
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    name: 'appQuickview',
    data: () => ({
      colors: ['success', 'info', 'danger', 'primary', 'warning', '']
    }),
    methods: {
      close () {
        this.$el.classList.remove('app-quickview-show')
      }
    }
  }
</script>
