<template>
  <div id="home">
    <div id="mainBody">
      <div id="leftHome">
        <p style="font-size: 60px">
          Welcome to <br />my <span style="color: #d90429;">I</span>nk
          <br /><span style="color: #d90429;">B</span>ookShop
        </p>

        <p style="font-size: 30px">
          Everything <br />you want to know <br />about history<br />
          is <em>Here</em>
        </p>
      </div>

      <div id="rightHome">
        <div>
          <p style="font-size: 30px; font-family: Bradley Hand;">
            Everyone loves these.
          </p>
        </div>
        <home-category-list></home-category-list>
      </div>
    </div>
  </div>
</template>

<script>
import HomeCategoryList from "@/components/HomeCategoryList";
export default {
  name: "Home",
  components: {
    HomeCategoryList
  }
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Proza+Libre");

#mainBody {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}

p {
  padding: 20px 10px 0px 10px;
  line-height: 120%;
}

#leftHome {
  padding-top: 70px;
  width: 300px;
  margin: 10px 30px 20px 80px;
  font-family: Bradley Hand;
  text-align: right;
}

#rightHome {
  margin: 10px 10px 20px 50px;
  width: 800px;
  display: flex;
  flex-direction: column;
}
</style>
