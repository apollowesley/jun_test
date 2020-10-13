<template>
  <div class="app bg-danger lt" data-ui-view>
    <div class="container text-center fade-in-up ng-enter">
      <div class="col-lg-6 col-md-6 offset-md-3 offset-lg-3">
        <div class="p-3 bg-white b-rad-30">
          <div class="error-shape err-500"><span>500</span></div>
          <div class="h3 text-grey">Internal server error</div>
          <div class="row">
            <div class="col-sm-8 offset-sm-2 text-grey">
              <div class="my-3 text-center">
                <textarea class="form-control" placeholder="Report the problem" style="height:150px;"> </textarea>
                <div class="clearfix mt-1">
                  <a href="/" class="btn btn-outline-warning rounded-circle btn-sm">Send</a>
                </div>
              </div>
              <div class="font-xs text-muted">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div>
            </div>
          </div>
          <div class="mt-3">
            <a href="/" class="btn btn-info rounded">Go home</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  export default { name: 'error-500' }
</script>
