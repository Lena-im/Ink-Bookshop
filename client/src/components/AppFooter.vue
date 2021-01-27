<template>
  <footer>
    <div id="leftFooter" style="color: #808080">
      &copy; 2020 Made by Nan
    </div>

    <div id="midFooter">
      <router-link to="#">Contacts</router-link>
      ｜
      <router-link to="#">Directions</router-link>
      ｜
      <router-link to="#">Privacy</router-link>
    </div>

    <div id="rightFooter">
      <router-link to="#">
        <img
          src="../assets/images/site/instagram-icon.png"
          alt="Instagram"
          style="height: 40px"
        />
      </router-link>

      <router-link to="#">
        <img
          src="../assets/images/site/twitter-icon.png"
          alt="Twitter"
          style="height: 40px"
        />
      </router-link>

      <router-link to="#">
        <img
          src="../assets/images/site/facebook-icon.png"
          alt="Facebook"
          style="height: 40px"
        />
      </router-link>
    </div>
  </footer>
</template>

<script>
export default {
  name: "AppFooter"
};
</script>

<style scoped>
footer {
  background: black;
  height: 60px;
  display: flex;
  justify-content: space-between;
}

#leftFooter {
  width: 200px;
  display: flex;
  align-self: center;
  padding-left: 10px;
}

#midFooter {
  width: 450px;
  display: flex;
  align-self: center;
  color: gray;
}

#rightFooter {
  width: 150px;
  display: flex;
  align-self: center;
}
</style>
