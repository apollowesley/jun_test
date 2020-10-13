<template>
	<div>
		<h2>Basic Dialog</h2>
		<LinkButton @click="$refs.d1.open()">Open Dialog</LinkButton>
		<Dialog ref="d1" :title="'Basic Dialog'" :dialogStyle="{width:'400px',height:'200px'}" :modal="true">
			<p style="text-align:center;margin:50px 0;font-size:16px">The Dialog Content.</p>
		</Dialog>

		<h2>Alert Messager</h2>
		<LinkButton @click="alert1()">Alert</LinkButton>
		<LinkButton @click="alert2()">Error</LinkButton>
		<LinkButton @click="alert3()">Info</LinkButton>
		<LinkButton @click="alert4()">Question</LinkButton>
		<LinkButton @click="alert5()">Warning</LinkButton>

	</div>
</template>

<script>
	export default {
		name: 'test',
		methods: {
			alert1() {
				this.$messager.alert({
					title: "Alert",
					msg: "Here is a message!"
				});
			},
			alert2() {
				this.$messager.alert({
					title: "Error",
					icon: "error",
					msg: "Here is an error message!"
				});
			},
			alert3() {
				this.$messager.alert({
					title: "Info",
					icon: "info",
					msg: "Here is a info message!"
				});
			},
			alert4() {
				this.$messager.alert({
					title: "Question",
					icon: "question",
					msg: "Here is a question message!"
				});
			},
			alert5() {
				this.$messager.alert({
					title: "Warning",
					icon: "warning",
					msg: "Here is a warning message!"
				});
			}
	}
}
</script>

<style>

</style>
