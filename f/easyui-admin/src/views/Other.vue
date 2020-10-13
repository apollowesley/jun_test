<template>
  <div>
    <h2>TextBox Addons</h2>
    <div style="margin-bottom:10px">
      <TextBox placeholder="With icons" style="width:300px">
        <Addon>
          <span class="textbox-icon icon-add"></span>
          <span class="textbox-icon icon-remove"></span>
          <span class="textbox-icon icon-search"></span>
        </Addon>
      </TextBox>
    </div>
    <div style="margin-bottom:10px">
      <TextBox placeholder="Icons on left" style="width:300px">
        <Addon align="left">
          <span class="textbox-icon icon-add"></span>
          <span class="textbox-icon icon-remove"></span>
          <span class="textbox-icon icon-search"></span>
        </Addon>
      </TextBox>
    </div>
    <div style="margin-bottom:10px">
      <TextBox placeholder="With button" style="width:300px;height:30px">
        <Addon>
          <LinkButton iconCls="icon-search" :btnStyle="{borderRadius:0,borderWidth:'0 0 0 1px',width:'70px',height:'30px'}">Search</LinkButton>
        </Addon>
      </TextBox>
    </div>
    <div style="margin-bottom:10px">
      <TextBox placeholder="With button" style="width:300px;height:30px">
        <Addon align="left">
          <LinkButton iconCls="icon-search" :btnStyle="{borderRadius:0,borderWidth:'0 1px 0 0',width:'70px',height:'30px'}">Search</LinkButton>
        </Addon>
        <Addon align="right">
          <span class="textbox-icon icon-add"></span>
          <span class="textbox-icon icon-remove"></span>
          <span class="textbox-icon icon-search"></span>
        </Addon>
      </TextBox>
    </div>

    <div>
      <h2>Basic ProgressBar</h2>
      <div style="margin-bottom:20px">
        <LinkButton :disabled="disabled" @click="run()">Start</LinkButton>
      </div>
      <ProgressBar :value="value"></ProgressBar>
    </div>

  </div>
</template>
 
<script>
export default {
  data() {
    return {
      value: 0,
      disabled: false
    };
  },
  methods: {
    run() {
      this.disabled = true;
      let timeout = Math.random() * 500;
      setTimeout(() => {
        this.value += Math.floor(Math.random() * 10);
        if (this.value > 100) {
          this.value = 100;
          this.disabled = false;
        } else {
          this.run();
        }
      }, timeout);
    }
  }
};
</script>