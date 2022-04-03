<template>
  <!--
  Copyright (c) 2021. Boltzbit Limited, United Kingdom. All Rights Reserved.
  This is a confidential file. Without a written licence by Boltzbit Limited,
  any copy, distribution or modification of this file is strictly prohibited.
  -->

  <div class="home">
    <!-- <img alt="Boltzbit logo" src="../assets/logo.png" /> -->
    <Console msg="Worker Manager Console" />
  </div>
</template>

<script>
// @ is an alias to /src
import Console from "@/components/Console.vue";

export default {
  name: "Home",
  components: {
    Console
  }
};
</script>
