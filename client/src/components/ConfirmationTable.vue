<template>
  <div id="confirmationTable">
    <table>
      <tr>
        <th></th>
        <th>Book Name</th>
        <th>Quantity</th>
        <th>Price</th>
      </tr>
      <tr
        v-for="(item, index) in this.$store.state.orderDetails.lineItems"
        :key="item.productId"
      >
        <img
          :src="
            require('@/assets/images/books/' +
              bookImageFileName($store.state.orderDetails.books[index]))
          "
          :alt="$store.state.orderDetails.books[index].title"
          style="height: 90px; width: 60px;"
        />
        <td>
          {{ $store.state.orderDetails.books[index].title }}
        </td>
        <td>{{ item.quantity }}</td>
        <td>
          {{
            ($store.state.orderDetails.books[index].price * item.quantity)
              | asDollarsAndCents
          }}
        </td>
      </tr>
      <tr class="noBorder price">
        <td>Subtotal:</td>
        <td></td>
        <td></td>
        <td>
          {{
            calculateSubtotal(
              $store.state.orderDetails.lineItems,
              $store.state.orderDetails.books
            ) | asDollarsAndCents
          }}
        </td>
      </tr>
      <tr class="price">
        <td>Shipping:</td>
        <td></td>
        <td></td>
        <td>{{ cart.surcharge | asDollarsAndCents }}</td>
      </tr>
      <tr class="noBorder price">
        <td>Total:</td>
        <td></td>
        <td></td>
        <td>
          {{
            (calculateSubtotal(
              $store.state.orderDetails.lineItems,
              $store.state.orderDetails.books
            ) +
              cart.surcharge)
              | asDollarsAndCents
          }}
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  name: "ConfirmationTable.vue",
  methods: {
    ...mapActions(["updateCart"]),
    bookImageFileName: function(book) {
      let name = book.title.toLowerCase();
      name = name.replace(/ /g, "-");
      return `${name}.png`;
    },
    calculateSubtotal: function(items, books) {
      let sum = 0;
      for (let i = 0; i < items.length; i++) {
        sum += books[i].price * items[i].quantity;
      }
      return sum;
    }
  },
  computed: {
    ...mapState(["cart"])
  }
};
</script>

<style scoped>
#confirmationTable {
  padding-top: 10px;
}

th {
  padding: 15px 35px;
  text-align: left;
}

img {
  padding: 10px 35px;
}

td {
  text-align: left;
  padding: 15px 35px;
}
tr {
  border-bottom: #1a1a1a solid 1px;
}

.noBorder {
  border: none;
}

.price > td {
  padding: 15px 35px;
}
</style>
