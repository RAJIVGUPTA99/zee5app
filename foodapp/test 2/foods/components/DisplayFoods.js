import PropTypes from "prop-types";
import React, { useEffect, useState } from "react";
import { connect } from "react-redux";
import paginate from "../../../utils/paginate";
import DisplayFoodCard from "./DisplayFoodCard";

const DisplayFoods = ({ auth: { loading }, allFoods }) => {
  const data = paginate(allFoods);
  const [page, setPage] = useState(0);
  const [foods, setFoods] = useState([]);

  useEffect(() => {
    setFoods(data[page]);
  }, [loading, data, page, foods]);

  const handlePage = (index) => {
    setPage(index);
  };

  const nextPage = () => {
    if (page === data.length - 1) {
      setPage(0);
    } else {
      setPage(page + 1);
    }
  };
  const prevPage = () => {
    if (page === 0) {
      setPage(data.length - 1);
    } else {
      setPage(page - 1);
    }
  };

  return (
    <main>
      <div className="profiles">
        {allFoods.length > 0 ? (
          foods.map((food) => <DisplayFoodCard key={food.id} food={food} />)
        ) : (
          <h4>No food found...</h4>
        )}
      </div>
      <nav className="pagination">
        <ul className="pagination">
          <li className="page-item">
            <button className="page-link" onClick={() => prevPage()}>
              {!loading ? "Previous" : null}
            </button>
          </li>
          {loading
            ? null
            : data.map((item, index) => {
                return (
                  <li
                    key={index}
                    className={`page-item ${index === page ? "active" : null}`}
                  >
                    <button
                      className="page-link"
                      onClick={() => {
                        handlePage(index);
                      }}
                    >
                      {index + 1}
                    </button>
                  </li>
                );
              })}
          <li className="page-item">
            <button className="page-link" onClick={() => nextPage()}>
              {!loading ? "Next" : null}
            </button>
          </li>
        </ul>
      </nav>
    </main>
  );
};

DisplayFoods.propTypes = {
  auth: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  auth: state.auth,
});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(DisplayFoods);
