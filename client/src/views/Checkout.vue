<template>
  <div>
    <section>
      <div v-if="cart.empty" id="emptyCheckOut">
        <p>
          Your cart is empty. You need to add an item to your cart to check out.
        </p>
        <router-link
          id="continueShopping"
          :to="{
            name: 'category',
            params: { name: $store.state.selectedCategoryName }
          }"
          >Continue Shopping</router-link
        >
      </div>

      <div v-else id="checkOut">
        <p style="font-size: 35px; font-weight: bold">Check Out</p>
      </div>
    </section>

    <section class="categoryView" v-if="!cart.empty">
      <form @submit.prevent="submitOrder">
        <span class="checkoutInfoLabel">Shipping Address</span>
        <div>
          <input
            type="text"
            size="20"
            id="name"
            name="name"
            placeholder="Name"
            v-model.lazy="$v.name.$model"
          />
        </div>
        <template v-if="$v.name.$error">
          <span class="error" v-if="!$v.name.required">* Name is required</span>
          <span class="error" v-else-if="!$v.name.minLength">
            * Name must have at least
            {{ $v.name.$params.minLength.min }} letters.
          </span>
          <span class="error" v-else-if="!$v.name.maxLength">
            * Name can have at most
            {{ $v.name.$params.maxLength.max }} letters.
          </span>
          <span class="error" v-else>
            * An unexpected error occurred.
          </span>
        </template>

        <div>
          <input
            type="text"
            size="20"
            id="address"
            name="address"
            placeholder="Address"
            v-model.lazy="$v.address.$model"
          />
        </div>
        <template v-if="$v.address.$error">
          <span class="error" v-if="!$v.address.required"
            >* Address is required</span
          >
          <span class="error" v-else-if="!$v.address.minLength">
            * Address must have at least
            {{ $v.address.$params.minLength.min }} letters.
          </span>
          <span class="error" v-else-if="!$v.address.maxLength">
            * Address can have at most
            {{ $v.name.$params.maxLength.max }} letters.
          </span>
          <span class="error" v-else>
            * An unexpected error occurred.
          </span>
        </template>

        <div>
          <input
            class="textField"
            type="text"
            size="20"
            id="phone"
            name="phone"
            placeholder="Phone"
            v-model.lazy="$v.phone.$model"
          />
        </div>
        <template v-if="$v.phone.$error">
          <span class="error" v-if="!$v.phone.required"
            >* Phone is required.</span
          >
          <span class="error" v-else-if="!$v.phone.phone"
            >* Please enter a valid phone number.</span
          >
          <span class="error" v-else>* An unexpected error occured.</span>
        </template>

        <span class="checkoutInfoLabel">Contact Information</span>
        <div>
          <input
            type="text"
            size="20"
            id="email"
            name="email"
            placeholder="Email"
            v-model.lazy="$v.email.$model"
          />
        </div>
        <template v-if="$v.email.$error">
          <span class="error" v-if="!$v.email.required"
            >* Email is required.</span
          >
          <span class="error" v-else-if="!$v.email.email"
            >* Please enter a valid email.</span
          >
          <span class="error" v-else>* An unexpected error occured.</span>
        </template>

        <span class="checkoutInfoLabel">Payment</span>
        <div>
          <input
            type="text"
            size="20"
            id="ccNumber"
            name="ccNumber"
            placeholder="Credit card"
            v-model.lazy="$v.ccNumber.$model"
          />
        </div>
        <template v-if="$v.ccNumber.$error">
          <span class="error" v-if="!$v.ccNumber.required"
            >* Credit card number is required.
          </span>
          <span class="error" v-else-if="!$v.ccNumber.creditCard"
            >* Please enter a valid credit card number.
          </span>
          <span class="error" v-else>* An unexpected error occured.</span>
        </template>

        <div>
          <label for="ccExpiryMonth">Exp Date</label>
          <select
            v-model="ccExpiryMonth"
            id="ccExpiryMonth"
            style="padding-right: 20px"
          >
            <option
              v-for="(month, index) in months"
              :key="index"
              :value="index + 1"
              >{{ month }} ({{ index + 1 }})</option
            >
          </select>

          <select v-model="ccExpiryYear" style="margin-left: 20px">
            <option v-for="(n, index) in 15" :key="index">{{
              yearFrom(index)
            }}</option>
          </select>
        </div>

        <section v-show="checkoutStatus != ''" class="checkoutStatusBox">
          <div v-if="checkoutStatus == 'ERROR'">
            <span style="color:red;"
              >* Error: Please fix the problems above and try again.</span
            >
          </div>

          <div v-else-if="checkoutStatus == 'PENDING'">
            Processing...
          </div>

          <div v-else-if="checkoutStatus == 'OK'">
            Order placed...
          </div>

          <div v-else>
            <span style="color: red"
              >* An unexpected error occurred, please try again.</span
            >
          </div>
        </section>

        <hr />
        <div class="chargeInfo">
          <span>Subtotal</span>
          <span>{{ cart.subtotal | asDollarsAndCents }}</span>
        </div>
        <div class="chargeInfo">
          <span>Shipping</span>
          <span>{{ cart.surcharge | asDollarsAndCents }}</span>
        </div>
        <hr />
        <div class="chargeInfo">
          <span>Total</span>
          <span>{{ cart.total | asDollarsAndCents }}</span>
        </div>

        <input
          type="submit"
          name="submit"
          :disabled="checkoutStatus == 'PENDING'"
          value="Complete Purchase"
        />
      </form>
    </section>
  </div>
</template>

<script>
import {
  required,
  email,
  minLength,
  maxLength
} from "vuelidate/lib/validators";

import isCreditCard from "validator/lib/isCreditCard";
import isMobilePhone from "validator/lib/isMobilePhone";

const phone = value => isMobilePhone(value, "en-US");
const creditCard = value => isCreditCard(value);

export default {
  data() {
    return {
      name: "",
      address: "",
      phone: "",
      email: "",
      ccNumber: "",
      ccExpiryMonth: new Date().getMonth() + 1,
      ccExpiryYear: new Date().getFullYear(),
      checkoutStatus: ""
    };
  },
  validations: {
    name: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(45)
    },
    address: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(45)
    },
    phone: {
      required,
      phone
    },
    email: {
      required,
      email
    },
    ccNumber: {
      required,
      creditCard
    }
  },

  computed: {
    cart() {
      return this.$store.state.cart;
    },
    months() {
      return [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
      ];
    }
  },

  methods: {
    submitOrder() {
      console.log("Submit order");
      this.$v.$touch(); // Ensures validators always run
      if (this.$v.$invalid) {
        this.checkoutStatus = "ERROR";
      } else {
        this.checkoutStatus = "PENDING";
        this.$store
          .dispatch("placeOrder", {
            name: this.name,
            address: this.address,
            phone: this.phone,
            email: this.email,
            ccNumber: this.ccNumber,
            ccExpiryMonth: this.ccExpiryMonth,
            ccExpiryYear: this.ccExpiryYear
          })
          .then(() => {
            this.checkoutStatus = "OK";
            this.$router.push({ name: "confirmation" });
          })
          .catch(reason => {
            this.checkoutStatus = "SERVER_ERROR";
            console.log("Error placing order", reason);
          });
      }
    },
    yearFrom(index) {
      return new Date().getFullYear() + index;
    }
  }
};
</script>

<style scoped>
#checkOut {
  color: black;
  font-family: AppleGothic;
  padding: 50px 80px 30px 0px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.categoryView {
  display: flex;
  align-items: center;
  justify-content: center;
}

form {
  display: flex;
  flex-direction: column;
}

.checkoutInfoLabel {
  color: black;
  font-family: AppleGothic;
  font-weight: bold;
  font-size: 18px;
  padding: 20px 0px 5px 0px;
}

form > div {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 0.5em;
  padding-top: 10px;
}

form > div > input,
form > div > select {
  width: 100%;
  padding: 12px 20px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  -webkit-transition: 0.5s;
  transition: 0.5s;
  border-radius: 4px;
  outline: none;
}

form > div > input:focus {
  outline: none;
  border: 2px solid #555;
}

form > div > label {
  margin-right: 10px;
  margin-top: 6px;
}

form > hr {
  width: 100%;
  color: #555555;
}

.error {
  color: red;
  padding-bottom: 10px;
}

input[type="submit"] {
  width: 100%;
  font-size: 20px;
  font-family: AppleGothic;
  color: #1a1a1a;
  padding: 14px 20px;
  text-decoration: none;
  display: inline-block;
  background-color: #cccccc;
  border: none;
  margin: 10px 20px 30px 0px;
  outline: none;
}

input[type="submit"]:hover {
  background-color: #999999;
}

.checkoutStatusBox {
  padding-top: 20px;
  padding-bottom: 20px;
}

#emptyCheckOut {
  color: black;
  font-family: AppleGothic;
  font-size: 30px;
  padding: 50px 80px 50px 50px;
  margin-left: 150px;
}

#continueShopping {
  font-size: 20px;
  font-family: AppleGothic;
  color: #1a1a1a;
  padding: 10px 25px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  background-color: #cccccc;
  margin-top: 50px;
}

#continueShopping:hover {
  background-color: #999999;
}

.chargeInfo {
  display: flex;
  justify-content: space-between;
}
</style>
