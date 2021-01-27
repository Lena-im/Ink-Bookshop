<template>
  <div id="confirmationView">
    <div id="orderDetails">
      <p style="font-size: 35px; font-weight: bold; padding-bottom: 30px">
        Order Confirmation
      </p>
      <br />
      <span class="confirmationTitle">ORDER DETAILS</span>
      <ul style="list-style-type: none">
        <li>
          Confirmation Number:
          <span style="font-weight: bold">{{
            $store.state.orderDetails.order.confirmationNumber
          }}</span>
        </li>
        <li>
          Ordered On:
          <span style="font-weight: bold">{{
            formatDate($store.state.orderDetails.order.dateCreated)
          }}</span>
        </li>
      </ul>
    </div>
    <div id="orderedProducts">
      <span class="confirmationTitle">ORDERED PRODUCTS</span>
      <confirmation-table> </confirmation-table>
    </div>
    <div id="shippingInfo">
      <span class="confirmationTitle">SHIPPING INFORMATION</span>
      <ul style="list-style-type: none">
        <li>
          Name:
          <span style="font-weight: bold">{{
            $store.state.orderDetails.customer.customerName
          }}</span>
        </li>
        <li>
          Address:
          <span style="font-weight: bold">{{
            $store.state.orderDetails.customer.address
          }}</span>
        </li>
        <li>
          Email:
          <span style="font-weight: bold">{{
            $store.state.orderDetails.customer.email
          }}</span>
        </li>
        <li>
          Phone Number:
          <span style="font-weight: bold">{{
            $store.state.orderDetails.customer.phone
          }}</span>
        </li>
        <li>
          Credit Card Info:
          <span style="font-weight: bold">
            {{ hideCCNumber($store.state.orderDetails.customer.ccNumber) }}
            （
            {{ $store.state.orderDetails.customer.ccExpDate.monthValue }}/
            {{ $store.state.orderDetails.customer.ccExpDate.year - 2000 }}）
          </span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import ConfirmationTable from "@/components/ConfirmationTable";
export default {
  name: "Confirmation.vue",
  components: {
    ConfirmationTable
  },
  methods: {
    hideCCNumber: function(number) {
      number = number.replace(/\d(?=\d{4})/g, "*");
      return number;
    },
    formatDate: function(dateObject) {
      return new Date(
        dateObject.year,
        dateObject.monthValue - 1,
        dateObject.dayOfMonth,
        dateObject.hour,
        dateObject.minute,
        dateObject.second
      ).toLocaleString();
    },
    formatExpireDate: function(dateObject) {
      return new Date(
        dateObject.year,
        dateObject.monthValue - 1
      ).toLocaleString();
    }
  }
};
</script>

<style scoped>
#orderDetails,
#orderedProducts,
#shippingInfo {
  color: black;
  font-family: AppleGothic;
  padding: 30px 80px 10px 200px;
}

.confirmationTitle {
  padding: 15px 20px;
  display: inline-block;
  width: 800px;
  background-color: #cccccc;
}

ul {
  padding: 20px 15px;
}

li {
  padding: 10px 15px;
}
</style>
