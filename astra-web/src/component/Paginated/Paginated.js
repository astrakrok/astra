import {useEffect, useState} from "react";
import {useSearchParams} from "react-router-dom";
import {app} from "../../constant/app";
import LoaderBoundary from "../LoaderBoundary/LoaderBoundary";
import DisplayBoundary from "../DisplayBoundary/DisplayBoundary";
import Pagination from "../Pagination/Pagination";
import "./Paginated.css";
import {mergeSearchParams} from "../../handler/params.handler";
import useDidChange from "../../hook/useDidChange";

const Paginated = ({
    children = {
        content: () => {},
        filter: () => {}
    },
    initialFilter = {},
    pageHandler = () => {},
    pageSize = app.pageSize
}) => {
    const [searchParams, setSearchParams] = useSearchParams();
    const [page, setPage] = useState({
        items: null,
        pagesCount: 1
    });
    const [filter, setFilter] = useState(initialFilter);

    const pageNumber = searchParams.get("page") * 1 || 1;

    const fetchPage = async pageNumber => {
        setPage(previous => ({
            ...previous,
            items: null
        }));
        const page = await pageHandler(filter, {pageSize: pageSize, pageNumber: pageNumber - 1});
        setPage(page);
    }

    useEffect(() => {
        changePage(pageNumber);
    }, []);

    useDidChange(() => {
        changePage(1);
    }, [filter]);

    const changePage = async newPageNumber => {
        if (pageNumber > page.totalPages || pageNumber < 1) {
            return;
        }
        await fetchPage(newPageNumber);
        setSearchParams(mergeSearchParams(searchParams, {page: newPageNumber}));
    }

    return (
        <div className="Paginated full-width">
            {
                children.filter({
                    initialFilter,
                    setFilter: filter => {
                        setFilter(filter)
                    }
                })
            }
            <div className="s-vflex">
                <LoaderBoundary condition={page.items == null} className="s-hflex-center loader">
                    {
                        page.items ? children.content(page.items, (pageNumber - 1) * pageSize) : null
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
