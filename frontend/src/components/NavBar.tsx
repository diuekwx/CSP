import React from 'react';
import { Link } from 'react-router-dom';

const Navbar: React.FC = () => {
  return (
    <nav style={{ padding: '1rem', background: '#222', color: '#fff' }}>
      <Link to="/home" style={{ color: 'white', textDecoration: 'none' }}>
        Home
      </Link>
    </nav>
  );
};

export default Navbar;
