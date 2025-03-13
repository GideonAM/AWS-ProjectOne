const Header = () => {
  return (
    <header className="bg-slate-200">
      <div className="flex justify-between items-center max-w-4xl mx-auto">
        <span className="font-bold text-sm sm:text-xl text-slate-500">
          File Server
        </span>
        <form className="bg-slate-100 p-3 rounded-lg">
          <input
            type="text"
            placeholder="search for a file ..."
            className="bg-transparent focus:outline-none"
          />
        </form>
      </div>
    </header>
  );
};

export default Header;
