class WorksController < ApplicationController
  def index
    list
    render :action => 'list'
  end

  # GETs should be safe (see http://www.w3.org/2001/tag/doc/whenToUseGet.html)
  verify :method => :post, :only => [ :destroy, :create, :update ],
         :redirect_to => { :action => :list }

  def list
    @work_pages, @works = paginate :works, :per_page => 10
  end

  def show
    @work = Work.find(params[:id])
  end

  def new
    @work = Work.new
  end

  def create
    @work = Work.new(params[:work])
    if @work.save
      flash[:notice] = 'Work was successfully created.'
      redirect_to :action => 'list'
    else
      render :action => 'new'
    end
  end

  def edit
    @work = Work.find(params[:id])
  end

  def update
    @work = Work.find(params[:id])
    if @work.update_attributes(params[:work])
      flash[:notice] = 'Work was successfully updated.'
      redirect_to :action => 'show', :id => @work
    else
      render :action => 'edit'
    end
  end

  def destroy
    Work.find(params[:id]).destroy
    redirect_to :action => 'list'
  end
end
