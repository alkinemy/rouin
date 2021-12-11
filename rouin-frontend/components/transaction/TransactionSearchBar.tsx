import React from "react";


const TransactionSearchBar: React.FC = () => {
    return (
        <div className="relative">
            <div className="absolute top-0 bottom-0 left-0 flex items-center px-5">
                <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 text-gray-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                </svg>
            </div>
            <input type="text" placeholder="Search for elixir..." className="pl-16 pr-4 py-4 rounded-md shadow-md bg-white border-0 w-full outline-none"/>
        </div>
    );
};

export default TransactionSearchBar;