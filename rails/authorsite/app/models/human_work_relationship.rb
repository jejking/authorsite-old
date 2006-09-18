class HumanWorkRelationship < ActiveRecord::Base
    
    belongs_to :human
    belongs_to :work
end
