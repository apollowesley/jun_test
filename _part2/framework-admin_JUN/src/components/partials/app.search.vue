<template>
  <div class="app-search" @click.self="close">
    <div class="app-search-wrapper">
      <div class="container pb-3">
        <div class="my-3 pb-0">
          <div class="input-holder item bg-light rad-30 p-b">
            <i class="ti-search"></i>
            <input class="form-control b-0 bg-no no-shadow" placeholder="Search..." autofocus>
            <div class="absolute t-0 r-0">
              <span class="btn btn-link no-shadow"><i class="fa fa-times fa-fw"></i></span>
            </div>
          </div>
        </div>
        <div class="mb-3">
          <div class="checkbox">
            <label class="ui-checkbox"><input type="checkbox" checked=""><i></i>Search within app</label>
            <label class="ui-checkbox"><input type="checkbox"><i></i>Extended</label>
          </div>
        </div>
        <div class="mb-3">
          <div class="bg-light bb-l">Suggestions</div>
          <div v-for="item in 2">Example of search suggestions</div>
        </div>
        <div>
          <div class="bg-light bb-l">Results</div>
          <div v-for="item in 3">
            <i class="fa fa-search"></i> <span class="h4 m-0">Lorem ipsum dolor</span>
            <div class="text-muted font-xs">Example of search results Example of search results Example of search results</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'appSearch',
    methods: {
      close () {
        console.log(this.$el)
        this.$el.classList.remove('app-search-show')
      }
    }
  }
</script>
