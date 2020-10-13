<template>
  <div>
    <div class="col-6">
      <Panel title="Basic Form" :collapsible="true" :bodyStyle="{padding:'20px'}">
        <Form :model="user" :labelWidth="120" labelAlign="right" style="max-width:500px">
          <FormField name="name" label="Name:">
            <TextBox v-model="user.name"></TextBox>
          </FormField>
          <FormField name="email" label="Email:">
            <TextBox v-model="user.email"></TextBox>
          </FormField>
          <FormField name="hero" label="Select a hero:">
            <ComboBox :data="heroes" v-model="user.hero"></ComboBox>
          </FormField>
          <FormField name="accept" label="Accept Me:">
            <CheckBox v-model="user.accept"></CheckBox>
          </FormField>
          <FormField>
            <LinkButton :disabled="false" @click="submitForm()" style="width:100px">Submit</LinkButton>
          </FormField>
        </Form>
      </Panel>
    </div>

    <div class="col-6">
      <Panel title="Validate Form" :collapsible="true" :bodyStyle="{padding:'20px'}">
        <Form ref="form" :model="user" :rules="rules" labelAlign="right" :labelWidth="120" @validate="errors=$event" style="max-width:500px">
          <FormField name="name" label="Name:">
            <TextBox v-model="user.name"></TextBox>
          </FormField>
          <FormField name="email" label="Email:">
            <TextBox v-model="user.email"></TextBox>
          </FormField>
          <FormField name="hero" label="Select a hero:">
            <ComboBox :data="heroes" v-model="user.hero"></ComboBox>
          </FormField>
          <FormField name="accept" label="Accept Me:">
            <CheckBox inputId="accept" name="accept" v-model="user.accept"></CheckBox>
          </FormField>
          <FormField>
            <LinkButton :disabled="false" @click="submitForm()" style="width:120px">Submit</LinkButton>
          </FormField>
        </Form>
      </Panel>
    </div>

    <div class="col-12">
      <Panel title="Validate Form with Tooltip" :collapsible="true" :bodyStyle="{padding:'20px'}">
        <Form ref="form" :model="user" :rules="rules" labelAlign="right" :labelWidth="120" errorType="tooltip" @validate="errors=$event" style="max-width:500px">
          <FormField name="name" label="Name:">
            <TextBox v-model="user.name"></TextBox>
          </FormField>
          <FormField name="email" label="Email:">
            <TextBox v-model="user.email"></TextBox>
          </FormField>
          <FormField name="hero" label="Select a hero:">
            <ComboBox :data="heroes" v-model="user.hero"></ComboBox>
          </FormField>
          <FormField name="accept" label="Accept Me:">
            <CheckBox inputId="accept" name="accept" v-model="user.accept"></CheckBox>
          </FormField>
          <FormField>
            <LinkButton :disabled="false" @click="submitForm()" style="width:120px">Submit</LinkButton>
          </FormField>
        </Form>
      </Panel>
    </div>

    <div class="col-12">
      <Panel title="Floating Label" :collapsible="true" :bodyStyle="{padding:'20px'}">
        <Form ref="form" :model="user" :rules="rules" labelPosition="top" :labelWidth="150" :floatingLabel="true" @validate="errors=$event" style="max-width:500px">
          <FormField name="name" label="Name">
            <TextBox v-model="user.name"></TextBox>
          </FormField>
          <FormField name="email" label="Email">
            <TextBox v-model="user.email"></TextBox>
          </FormField>
          <FormField name="hero" label="Select a hero">
            <ComboBox :data="heroes" v-model="user.hero"></ComboBox>
          </FormField>
          <FormField name="accept" label="Accept Me" labelPosition="after">
            <CheckBox v-model="user.accept"></CheckBox>
          </FormField>
          <FormField>
            <LinkButton :disabled="false" @click="submitForm()">Submit</LinkButton>
          </FormField>
        </Form>
      </Panel>
    </div>


    <p>{{user}}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: {
        name: null,
        email: null,
        hero: null,
        accept: false
      },
      rules: {
        name: ["required", "length[5,10]"],
        email: "email",
        hero: "required"
      },
      heroes: [{
        value: 11,
        text: "Mr. Nice"
      },
      {
        value: 12,
        text: "Narco"
      },
      {
        value: 13,
        text: "Bombasto"
      },
      {
        value: 14,
        text: "Celeritas"
      },
      {
        value: 15,
        text: "Magneta"
      },
      {
        value: 16,
        text: "RubberMan"
      },
      {
        value: 17,
        text: "Dynama"
      },
      {
        value: 18,
        text: "Dr IQ"
      },
      {
        value: 19,
        text: "Magma"
      },
      {
        value: 20,
        text: "Tornado"
      }
      ]
    };
  },
  methods: {
    submitForm() {
      this.$refs.form.validate((valid) => {
        window.console.log(valid)
      })
    }
  }
};
</script>


<style scoped>
.col-6 {
  width: 48%;
  margin-left: 2%;
  margin-bottom: 1%;
  float: left;
}
.col-12 {
  clear: both;
  margin-left: 2%;
  margin-bottom: 1%;
}
</style>
