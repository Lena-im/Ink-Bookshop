<template>
  <section id="cartTable">
    <table>
      <tr v-for="item in cart.items" :key="item.book.bookId">
        <img
          class="bookImage"
          :src="
            require('@/assets/images/books/' + bookImageFileName(item.book))
          "
          :alt="item.book.title"
          style="height: 90px; width: 60px;"
        />
        <td>{{ item.book.title }}</td>
        <td>
          <input
            v-on:change="quantityChange($event, item, cart)"
            type="number"
            :value="item.quantity"
            min="0"
            max="99"
          />
        </td>

        <td>
          {{ (item.book.price * item.quantity) | asDollarsAndCents }}
        </td>
      </tr>
    </table>
  </section>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  name: "CartTable.vue",
  methods: {
    ...mapActions(["updateCart"]),
    bookImageFileName: function(book) {
      let name = book.title.toLowerCase();
      name = name.replace(/ /g, "-");
      return `${name}.png`;
    },
    quantityChange: function(event, item, cart) {
      this.$store.dispatch("updateCart", {
        book: item.book,
        quantity: event.target.value
      });
      cart.update(item.book, event.target.value);
    }
  },
  computed: {
    ...mapState(["cart"])
  }
};
</script>

<style scoped>
#cartTable {
  padding-left: 200px;
  padding-right: 200px;
}

#cartTable img {
  padding: 20px 30px 10px 10px;
}

#cartTable td {
  padding: 20px 30px 10px 20px;
  font-family: AppleGothic;
  font-size: 15px;
}

#cartTable tr {
  border-bottom: #1a1a1a solid 1px;
}
</style>
