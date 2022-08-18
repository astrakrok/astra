import {useEffect, useState} from "react";
import {useSearchParams} from "react-router-dom";
import {app} from "../../constant/app";
import LoaderBoundary from "../LoaderBoundary/LoaderBoundary";
import DisplayBoundary from "../DisplayBoundary/DisplayBoundary";
import Pagination from "../Pagination/Pagination";
import "./Paginated.css";

const Paginated = ({
                       children = () => {
                       },
                       pageHandler = () => {
                       },
                       pageSize = app.pageSize
                   }) => {
    const [searchParams, setSearchParams] = useSearchParams();
    const [page, setPage] = useState({
        items: null,
        pagesCount: 1
    });

    const pageNumber = searchParams.get("page") * 1 || 1;

    const fetchPage = async pageNumber => {
        setPage(previous => ({
            ...previous,
            items: null
        }));
        console.log(pageNumber, pageSize);
        const page = await pageHandler(pageSize, pageNumber - 1);
        setPage(page);
    }

    useEffect(() => {
        fetchPage(pageNumber);
    }, []);

    const changePage = async newPageNumber => {
        if (pageNumber > page.totalPages || pageNumber < 1) {
            return;
        }
        await fetchPage(newPageNumber);
        setSearchParams({
            page: newPageNumber
        });
    }

    return (
        <div className="Paginated full-width">
            <div className="s-vflex">
                <LoaderBoundary condition={page.items == null} className="s-hflex-center loader">
                    {
                        page.items ? children(page.items, (pageNumber - 1) * pageSize) : null
                    }
                </LoaderBoundary>
                <DisplayBoundary condition={page.pagesCount > 1}>
                    <div className="s-hflex-center">
                        <Pagination
                            pagesCount={page.pagesCount}
                            currentPage={pageNumber}
                            onPreviousPageClicked={() => changePage(pageNumber - 1)}
                            onPageClicked={page => changePage(page)}
                            onNextPageClicked={() => changePage(pageNumber + 1)}/>
                    </div>
                </DisplayBoundary>
            </div>
        </div>
    );
}

export default Paginated;
