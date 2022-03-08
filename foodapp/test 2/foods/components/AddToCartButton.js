import PropTypes from "prop-types";
import React from "react";
import { connect } from "react-redux";

const AddToCartButton = ({ food }) => {
  return (
    <div>
      {/* <div ngIf="isInCart">
        <button
          class="btn btn-primary btn-update-cart"
          click="decrementQuantity()"
        >
          -
        </button>
        <span class="quantity-container">1</span>
        <button class="btn btn-primary btn-update-cart">
          +
        </button>
      </div> */}

      <div>
        <button class="btn btn-primary">Add to cart</button>
      </div>
    </div>
  );
};

AddToCartButton.propTypes = {};

const mapStateToProps = (state) => ({});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(AddToCartButton);
