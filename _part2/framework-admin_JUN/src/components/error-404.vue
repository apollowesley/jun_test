<template>
  <div class="app bg-warning lt h-100p">
    <div class="container text-center fade-in-up ng-enter">
      <div class="col-lg-6 col-md-6 offset-md-3 offset-lg-3">
        <div class="bg-white b-rad-30 p-3">
          <div class="error-shape err-404"><span>404</span></div>
          <div class="h3 text-grey">The Page you requested does not exist</div>
          <div class="row text-grey">
            <div class="col-md-8 offset-md-2">
              <div class="my-3 text-center">
                <input class="form-control rounded-circle inline-block" placeholder="Search something">
              </div>
              <div class="font-xs text-muted">*Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div>
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
  export default {
    name: 'error-404'
  }
</script>
