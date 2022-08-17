import {range} from "../../handler/array.handler";
import "./Pagination.css";

const Pagination = ({
                        pagesCount,
                        currentPage = 0,
                        onPageClicked = () => {
                        },
                        onPreviousPageClicked = () => {
                        },
                        onNextPageClicked = () => {
                        }
                    }) => {
    const renderPages = (from, to, selected) => {
        return range(from, to).map(item => (
            <div key={item} className={`navigation ${selected === item ? "selected" : ""}`}
                 onClick={() => onPageClicked(item)}>
                {item}
            </div>
        ));
    }

    const renderDots = () => {
        return (
            <div className="navigation dots">
                ...
            </div>
        )
    }

    return (
        <div className="Pagination s-hflex wrap-flex">
            <div className={`previous navigation ${currentPage === 1 ? "disabled" : ""}`}
                 onClick={onPreviousPageClicked}>
                <i className="material-icons">chevron_left</i>
            </div>
            {
                currentPage <= 4 ? (
                    renderPages(1, currentPage, currentPage)
                ) : (
                    <>
                        {renderPages(1, 1)}
                        {renderDots()}
                        {renderPages(currentPage - 1, currentPage, currentPage)}
                    </>
                )
            }
            {
                pagesCount - currentPage <= 3 ? (
                    pagesCount > currentPage ? (
                        renderPages(currentPage + 1, pagesCount)
                    ) : null
                ) : (
                    <>
                        {renderPages(currentPage + 1, currentPage + 1)}
                        {renderDots()}
                        {renderPages(pagesCount - 1, pagesCount)}
                    </>
                )
            }
            <div className={`previous navigation ${currentPage === pagesCount ? "disabled" : ""}`}
                 onClick={onNextPageClicked}>
                <i className="material-icons">chevron_right</i>
            </div>
        </div>
    );
}

export default Pagination;
