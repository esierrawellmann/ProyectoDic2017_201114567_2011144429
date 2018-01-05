package com.edd.player.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.List;


public class DTOLogin {

    public boolean success;
    public Data data;
    public static class Data{

        public List<Year> years = new ArrayList<Year>();

        public Data() {
        }

        public List<Year> getYears() {
            return years;
        }

        public void setYears(List<Year> years) {
            this.years = years;
        }

        public static class Year{

            public Long index;
            public String year;
            public List<Genere> generos = new ArrayList<Genere>();

            public Year() {
            }

            @Override
            public String toString() {
                return year;
            }

            public Long getIndex() {
                return index;
            }

            public void setIndex(Long index) {
                this.index = index;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public List<Genere> getGeneros() {
                return generos;
            }

            public void setGeneros(List<Genere> generos) {
                this.generos = generos;
            }

            public static class Genere{

                public Long index;
                public List<Artist> artistas = new ArrayList<Artist>();
                public String genero;

                public Genere() {
                }

                @Override
                public String toString() {
                    return genero;
                }

                public List<Artist> getArtistas() {
                    return artistas;
                }

                public void setArtistas(List<Artist> artistas) {
                    this.artistas = artistas;
                }

                public Long getIndex() {
                    return index;
                }

                public void setIndex(Long index) {
                    this.index = index;
                }



                public String getGenero() {
                    return genero;
                }

                public void setGenero(String genero) {
                    this.genero = genero;
                }

                public static class Artist{
                    public String artista;
                    public List<Album> albums = new ArrayList<Album>();
                    public Artist(String artista) {
                        this.artista = artista;
                    }

                    public Artist() {
                    }

                    @Override
                    public String toString() {
                        return artista;
                    }

                    public List<Album> getAlbums() {
                        return albums;
                    }

                    public void setAlbums(List<Album> albums) {
                        this.albums = albums;
                    }

                    public String getArtista() {
                        return artista;
                    }

                    public void setArtista(String artista) {
                        this.artista = artista;
                    }



                    public static class Album{
                        public String nombre;
                        public List<Cancion> canciones  = new ArrayList<Cancion>();

                        public Album() {
                        }

                        @Override
                        public String toString() {
                            return nombre;
                        }

                        public String getNombre() {
                            return nombre;
                        }

                        public void setNombre(String nombre) {
                            this.nombre = nombre;
                        }

                        public List<Cancion> getCanciones() {
                            return canciones;
                        }

                        public void setCanciones(List<Cancion> canciones) {
                            this.canciones = canciones;
                        }

                        public static class Cancion{

                            public Long index;
                            public String nombre_cancion;
                            public String ruta;

                            public Cancion() {
                            }

                            @Override
                            public String toString() {
                                return nombre_cancion;
                            }

                            public Long getIndex() {
                                return index;
                            }

                            public void setIndex(Long index) {
                                this.index = index;
                            }

                            public String getNombre_cancion() {
                                return nombre_cancion;
                            }

                            public void setNombre_cancion(String nombre_cancion) {
                                this.nombre_cancion = nombre_cancion;
                            }

                            public String getRuta() {
                                return ruta;
                            }

                            public void setRuta(String ruta) {
                                this.ruta = ruta;
                            }
                        }
                    }
                }
            }
        }
    }

}
