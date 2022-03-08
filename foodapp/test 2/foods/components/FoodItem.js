import PropTypes from "prop-types";
import React, { useEffect, Fragment } from "react";
import { connect } from "react-redux";
import { Link } from "react-router-dom";
import { useParams } from "react-router-dom";
import { getFoodById } from "../actions/foodActions";

export const FoodItem = ({ food: { food }, getFoodById }) => {
  const { id } = useParams();

  useEffect(() => {
    getFoodById(id);
  }, [getFoodById, id]);

  return food ? (
    <div className="row food-details-container">
      <div className="col">
        <img
          src={food.foodPic}
          className="card-img-top d-inline-block"
          alt={food.foodName}
        />
      </div>
      <div className="col">
        <Link to="/" className="btn btn-light mr-auto">
          Back To Foods
        </Link>

        <div>
          <h3>{food.foodName}</h3>
          <h1>₹ {food.foodCost}</h1>
          <h2>{food.foodType}</h2>

          <div>{food.description}</div>
        </div>
      </div>
    </div>
  ) : (
    <h4>No Food Found</h4>
  );
};

FoodItem.propTypes = {
  food: PropTypes.object.isRequired,
  getFoodById: PropTypes.func.isRequired,
};

const mapStateToProps = (state) => ({
  food: state.food,
});

const mapDispatchToProps = { getFoodById };

export default connect(mapStateToProps, mapDispatchToProps)(FoodItem);
