<template>
  <div class="bookBlock">
    <div class="bookFrame">
      <img src="../assets/images/site/frame.png" style="height: 230px" />
    </div>
    <router-link to="#">
      <img
        class="bookImage"
        :src="require('@/assets/images/books/' + bookImageFileName(book))"
        :alt="book.title"
        style="height: 180px; width: 120px;"
      />
    </router-link>
    <div class="bookInfo">
      <h1>{{ book.title }}</h1>
      <h2>{{ book.author }}</h2>
      <h3>${{ (book.price / 100).toFixed(2) }}</h3>
    </div>
    <ul class="bookButtons">
      <li @click="addToCart(book)" class="addToCartButton">
        <router-link class="bookButtonLink" to="#"
          ><img
            src="../assets/images/site/addToCart.png"
            alt="add to cart"
            style="height: 20px; width: 25px"
          />
          Add to Cart
        </router-link>
      </li>
      <li v-if="book.isPublic" class="readNowButton">
        <router-link class="bookButtonLink" to="#"
          ><img
            src="../assets/images/site/readNow.png"
            alt="read now"
            style="height: 20px; width: 25px"
          />
          Read Now
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "categoryBookListItem",
  props: {
    book: {
      type: Object,
      required: true
    }
  },
  methods: {
    bookImageFileName: function(book) {
      let name = book.title.toLowerCase();
      name = name.replace(/ /g, "-");
      return `${name}.png`;
    },
    addToCart: function(book) {
      this.$store.dispatch("addToCart", book);
    }
  }
};
</script>

<style scoped>
.bookBlock {
  padding: 20px 10px 20px 10px;
  position: relative;
}

.bookImage {
  position: absolute;
  top: 45px;
  left: 30px;
}

.bookInfo {
  position: absolute;
  top: 50px;
  left: 165px;
}

h1 {
  font-family: Bradley Hand;
  font-size: 25px;
  margin: 3px;
  cursor: pointer;
}

h2 {
  font-family: AppleGothic;
  color: #7f7f7f;
  margin-top: 10px;
  margin-left: 3px;
}

h3 {
  font-size: 15px;
  font-family: AppleGothic;
  font-weight: bold;
  padding-top: 5px;
  color: #d90429;
  margin-top: 10px;
  margin-left: 3px;
}

.bookButtons {
  font-size: 15px;
  font-family: AppleGothic;
  font-weight: bold;
  position: absolute;
  top: 165px;
  left: 165px;
}

.bookButtons a {
  color: black;
  display: flex;
  align-items: center;
}

.bookButtons a:hover {
  font-weight: bold;
  color: #d90429;
}

.bookButtons img {
  padding-top: 5px;
  padding-right: 10px;
  padding-bottom: 5px;
}

.bookButtonLink {
  display: inline-block;
}
</style>
